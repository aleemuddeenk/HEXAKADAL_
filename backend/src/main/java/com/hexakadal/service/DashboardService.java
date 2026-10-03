package com.hexakadal.service;

import com.hexakadal.model.DashboardSummary;
import com.hexakadal.model.OptimizationRequest;
import com.hexakadal.model.OptimizationResponse;
import com.hexakadal.model.WeatherData;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final OptimizationService optimizationService;
    private final WeatherService weatherService;
    private final FreightService freightService;
    private final AlertService alertService;

    public DashboardService(OptimizationService optimizationService, WeatherService weatherService, FreightService freightService, AlertService alertService) {
        this.optimizationService = optimizationService;
        this.weatherService = weatherService;
        this.freightService = freightService;
        this.alertService = alertService;
    }

    public DashboardSummary getDashboardSummary() {
        OptimizationRequest optReq = new OptimizationRequest(150000, "Hay Point (Australia)", "Paradip Port", 30000.0, 17.50);
        OptimizationResponse optRes = optimizationService.runOptimization(optReq);
        WeatherData weather = weatherService.getPortWeatherTelemetry("Paradip Port", optRes.getVesselDraft());
        double currentSpot = freightService.getCurrentSpotRate();
        double forecastRate = Math.round(currentSpot * 0.88 * 100.0) / 100.0;

        return new DashboardSummary(
            optRes.getNetDecisionValue(),
            optRes.getPotentialFreightSavings(),
            optRes.getDemurrageCost(),
            weather.getTotalAnchorageQueueHours(),
            optRes.getExecutiveSignal(),
            optRes.getXaiReason(),
            optRes.getSelectedVessel(),
            optRes.getVesselDraft(),
            optRes.getPortMaxDraft(),
            currentSpot,
            forecastRate,
            weather.getWaveHeight(),
            weather.getWeatherStatus(),
            weather.getWaitingVessels(),
            alertService.getActiveAlerts()
        );
    }
}
