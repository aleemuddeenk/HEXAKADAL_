package com.hexakadal.optimization;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.hexakadal.model.OptimizationRequest;
import com.hexakadal.model.OptimizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VesselRoutingOptimizer {

    private static final Logger log = LoggerFactory.getLogger(VesselRoutingOptimizer.class);
    private boolean isOrToolsLoaded = false;

    public VesselRoutingOptimizer() {
        initOrTools();
    }

    private void initOrTools() {
        try {
            log.info("Initializing Google OR-Tools Java API...");
            Loader.loadNativeLibraries();
            isOrToolsLoaded = true;
            log.info("Google OR-Tools system libraries loaded successfully!");
        } catch (Throwable t) {
            log.warn("Google OR-Tools native libraries could not be loaded ({}); using Java Mathematical Solver engine.", t.getMessage());
            isOrToolsLoaded = false;
        }
    }

    public OptimizationResponse optimizeVesselChartering(
            OptimizationRequest request,
            double currentSpotRate,
            double forecastSpotRate,
            double queueHours) {

        double cargoQty = request.getCargoQuantity();
        String destPort = request.getDestinationPort();

        // Standard port draft database mapping
        Map<String, Double> defaultPortDrafts = new HashMap<>();
        defaultPortDrafts.put("Paradip Port", 17.50);
        defaultPortDrafts.put("Visakhapatnam Port", 16.10);
        defaultPortDrafts.put("Haldia Port", 8.50);
        defaultPortDrafts.put("Dhamra Port", 18.00);
        defaultPortDrafts.put("Gopalpur Port", 14.50);

        double portMaxDraft = request.getPortMaxDraftOverride() != null ?
                request.getPortMaxDraftOverride() :
                defaultPortDrafts.getOrDefault(destPort, 15.00);

        // Candidate Vessels
        VesselOption[] candidateVessels = new VesselOption[]{
            new VesselOption("MV ORE BRASIL (400k DWT Valemax)", 400000, 18.0, 45000.0),
            new VesselOption("MV CAPESIZE HERO (180k DWT Capesize)", 180000, 16.5, 30000.0),
            new VesselOption("MV PANAMAX STAR (75k DWT Panamax)", 75000, 12.0, 20000.0),
            new VesselOption("MV SUPRAMAX OCEAN (55k DWT Supramax)", 55000, 9.0, 15000.0)
        };

        VesselOption selected = null;
        String solverStatus = "OPTIMAL";
        String solverType = "OR-Tools Constraint Solver (CBC)";

        if (isOrToolsLoaded) {
            try {
                MPSolver solver = MPSolver.createSolver("CBC");
                if (solver != null) {
                    MPVariable[] x = new MPVariable[candidateVessels.length];
                    for (int i = 0; i < candidateVessels.length; i++) {
                        x[i] = solver.makeBoolVar("vessel_" + i);
                    }

                    // Constraint 1: Select exactly one vessel
                    MPConstraint cOneVessel = solver.makeConstraint(1.0, 1.0, "SelectOneVessel");
                    for (int i = 0; i < candidateVessels.length; i++) {
                        cOneVessel.setCoefficient(x[i], 1.0);
                    }

                    // Constraint 2: Capacity requirement
                    MPConstraint cCapacity = solver.makeConstraint(cargoQty, Double.POSITIVE_INFINITY, "CapacityConstraint");
                    for (int i = 0; i < candidateVessels.length; i++) {
                        cCapacity.setCoefficient(x[i], candidateVessels[i].dwt);
                    }

                    // Constraint 3: Draft depth limit (draft <= portMaxDraft)
                    MPConstraint cDraft = solver.makeConstraint(Double.NEGATIVE_INFINITY, portMaxDraft, "DraftConstraint");
                    for (int i = 0; i < candidateVessels.length; i++) {
                        cDraft.setCoefficient(x[i], candidateVessels[i].draft);
                    }

                    // Objective: Minimize Demurrage + Daily charter cost
                    MPObjective objective = solver.objective();
                    for (int i = 0; i < candidateVessels.length; i++) {
                        double totalEstCost = candidateVessels[i].dailyDemurrageRate * (queueHours / 24.0);
                        objective.setCoefficient(x[i], totalEstCost);
                    }
                    objective.setMinimization();

                    MPSolver.ResultStatus resultStatus = solver.solve();
                    if (resultStatus != null && (resultStatus == MPSolver.ResultStatus.OPTIMAL || resultStatus == MPSolver.ResultStatus.FEASIBLE)) {
                        for (int i = 0; i < candidateVessels.length; i++) {
                            if (x[i].solutionValue() > 0.5) {
                                selected = candidateVessels[i];
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("OR-Tools solver execution exception: {}. Falling back to Rule Solver.", e.getMessage());
            }
        }

        // Rule-based fallback/verifying logic
        if (selected == null) {
            solverType = "Rule-Based Deterministic Optimizer";
            if (cargoQty >= 200000 && portMaxDraft >= 17.0) {
                selected = candidateVessels[0];
            } else if (cargoQty >= 100000 && portMaxDraft >= 16.0) {
                selected = candidateVessels[1];
            } else if (cargoQty >= 60000 && portMaxDraft >= 11.0) {
                selected = candidateVessels[2];
            } else {
                selected = candidateVessels[3];
            }
        }

        // Override demurrage rate if requested
        double demurrageRate = request.getDemurrageRateOverride() != null ?
                request.getDemurrageRateOverride() :
                selected.dailyDemurrageRate;

        // Core Financial Math
        double rateDelta = currentSpotRate - forecastSpotRate;
        double potentialFreightSavings = rateDelta * cargoQty;
        double demurrageCost = (queueHours / 24.0) * demurrageRate;
        double netDecisionValue = potentialFreightSavings - demurrageCost;

        boolean isDraftSafe = selected.draft <= portMaxDraft;

        // Executive Action Signal Generation Matrix
        String signal;
        String xaiReason;

        if (!isDraftSafe) {
            signal = "WATCH / REROUTE";
            xaiReason = String.format("CRITICAL DRAFT VIOLATION: Selected vessel draft (%.2fm) exceeds port depth limit (%.2fm) at %s.",
                    selected.draft, portMaxDraft, destPort);
        } else if (netDecisionValue > 15000) {
            signal = "WAIT / DEFER FIXING";
            xaiReason = String.format("Rate forecasted to drop from $%.2f/MT to $%.2f/MT. Expected Net Savings after Demurrage: $%,.2f.",
                    currentSpotRate, forecastSpotRate, netDecisionValue);
        } else if (netDecisionValue < -10000) {
            signal = "ENTER / FIX NOW";
            xaiReason = String.format("High port congestion queue (%.1f hrs) generates severe demurrage risk ($%,.2f). Lock charter party immediately.",
                    queueHours, demurrageCost);
        } else {
            signal = "WATCH";
            xaiReason = String.format("Market is stable. Net Decision Value ($%,.2f) is within normal operational tolerance.",
                    netDecisionValue);
        }

        return new OptimizationResponse(
            selected.name,
            selected.draft,
            portMaxDraft,
            demurrageRate,
            Math.round(netDecisionValue * 100.0) / 100.0,
            Math.round(potentialFreightSavings * 100.0) / 100.0,
            Math.round(demurrageCost * 100.0) / 100.0,
            isDraftSafe,
            signal,
            xaiReason,
            solverStatus,
            solverType
        );
    }

    private static class VesselOption {
        String name;
        long dwt;
        double draft;
        double dailyDemurrageRate;

        VesselOption(String name, long dwt, double draft, double dailyDemurrageRate) {
            this.name = name;
            this.dwt = dwt;
            this.draft = draft;
            this.dailyDemurrageRate = dailyDemurrageRate;
        }
    }
}
