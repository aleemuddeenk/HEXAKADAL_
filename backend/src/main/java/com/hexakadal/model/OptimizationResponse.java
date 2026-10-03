package com.hexakadal.model;

public class OptimizationResponse {
    private String selectedVessel;
    private double vesselDraft;
    private double portMaxDraft;
    private double demurrageRate;
    private double netDecisionValue;
    private double potentialFreightSavings;
    private double demurrageCost;
    private boolean isDraftSafe;
    private String executiveSignal;
    private String xaiReason;
    private String solutionStatus;
    private String solverType;

    public OptimizationResponse() {}

    public OptimizationResponse(String selectedVessel, double vesselDraft, double portMaxDraft, double demurrageRate, double netDecisionValue, double potentialFreightSavings, double demurrageCost, boolean isDraftSafe, String executiveSignal, String xaiReason, String solutionStatus, String solverType) {
        this.selectedVessel = selectedVessel;
        this.vesselDraft = vesselDraft;
        this.portMaxDraft = portMaxDraft;
        this.demurrageRate = demurrageRate;
        this.netDecisionValue = netDecisionValue;
        this.potentialFreightSavings = potentialFreightSavings;
        this.demurrageCost = demurrageCost;
        this.isDraftSafe = isDraftSafe;
        this.executiveSignal = executiveSignal;
        this.xaiReason = xaiReason;
        this.solutionStatus = solutionStatus;
        this.solverType = solverType;
    }

    public String getSelectedVessel() { return selectedVessel; }
    public void setSelectedVessel(String selectedVessel) { this.selectedVessel = selectedVessel; }

    public double getVesselDraft() { return vesselDraft; }
    public void setVesselDraft(double vesselDraft) { this.vesselDraft = vesselDraft; }

    public double getPortMaxDraft() { return portMaxDraft; }
    public void setPortMaxDraft(double portMaxDraft) { this.portMaxDraft = portMaxDraft; }

    public double getDemurrageRate() { return demurrageRate; }
    public void setDemurrageRate(double demurrageRate) { this.demurrageRate = demurrageRate; }

    public double getNetDecisionValue() { return netDecisionValue; }
    public void setNetDecisionValue(double netDecisionValue) { this.netDecisionValue = netDecisionValue; }

    public double getPotentialFreightSavings() { return potentialFreightSavings; }
    public void setPotentialFreightSavings(double potentialFreightSavings) { this.potentialFreightSavings = potentialFreightSavings; }

    public double getDemurrageCost() { return demurrageCost; }
    public void setDemurrageCost(double demurrageCost) { this.demurrageCost = demurrageCost; }

    public boolean isDraftSafe() { return isDraftSafe; }
    public void setDraftSafe(boolean draftSafe) { isDraftSafe = draftSafe; }

    public String getExecutiveSignal() { return executiveSignal; }
    public void setExecutiveSignal(String executiveSignal) { this.executiveSignal = executiveSignal; }

    public String getXaiReason() { return xaiReason; }
    public void setXaiReason(String xaiReason) { this.xaiReason = xaiReason; }

    public String getSolutionStatus() { return solutionStatus; }
    public void setSolutionStatus(String solutionStatus) { this.solutionStatus = solutionStatus; }

    public String getSolverType() { return solverType; }
    public void setSolverType(String solverType) { this.solverType = solverType; }
}
