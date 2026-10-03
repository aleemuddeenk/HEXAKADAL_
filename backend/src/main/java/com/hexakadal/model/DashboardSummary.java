package com.hexakadal.model;

import java.util.List;

public class DashboardSummary {
    private double ndv;
    private double freightSavings;
    private double demurrageLoss;
    private double totalCongestionQueueHours;
    private String executiveSignal;
    private String xaiReason;
    private String selectedVessel;
    private double vesselDraft;
    private double portMaxDraft;
    private double currentSpotRate;
    private double targetForecastRate;
    private double liveWaveHeight;
    private String weatherStatus;
    private int waitingVesselsCount;
    private List<Alert> activeAlerts;

    public DashboardSummary() {}

    public DashboardSummary(double ndv, double freightSavings, double demurrageLoss, double totalCongestionQueueHours, String executiveSignal, String xaiReason, String selectedVessel, double vesselDraft, double portMaxDraft, double currentSpotRate, double targetForecastRate, double liveWaveHeight, String weatherStatus, int waitingVesselsCount, List<Alert> activeAlerts) {
        this.ndv = ndv;
        this.freightSavings = freightSavings;
        this.demurrageLoss = demurrageLoss;
        this.totalCongestionQueueHours = totalCongestionQueueHours;
        this.executiveSignal = executiveSignal;
        this.xaiReason = xaiReason;
        this.selectedVessel = selectedVessel;
        this.vesselDraft = vesselDraft;
        this.portMaxDraft = portMaxDraft;
        this.currentSpotRate = currentSpotRate;
        this.targetForecastRate = targetForecastRate;
        this.liveWaveHeight = liveWaveHeight;
        this.weatherStatus = weatherStatus;
        this.waitingVesselsCount = waitingVesselsCount;
        this.activeAlerts = activeAlerts;
    }

    public double getNdv() { return ndv; }
    public void setNdv(double ndv) { this.ndv = ndv; }

    public double getFreightSavings() { return freightSavings; }
    public void setFreightSavings(double freightSavings) { this.freightSavings = freightSavings; }

    public double getDemurrageLoss() { return demurrageLoss; }
    public void setDemurrageLoss(double demurrageLoss) { this.demurrageLoss = demurrageLoss; }

    public double getTotalCongestionQueueHours() { return totalCongestionQueueHours; }
    public void setTotalCongestionQueueHours(double totalCongestionQueueHours) { this.totalCongestionQueueHours = totalCongestionQueueHours; }

    public String getExecutiveSignal() { return executiveSignal; }
    public void setExecutiveSignal(String executiveSignal) { this.executiveSignal = executiveSignal; }

    public String getXaiReason() { return xaiReason; }
    public void setXaiReason(String xaiReason) { this.xaiReason = xaiReason; }

    public String getSelectedVessel() { return selectedVessel; }
    public void setSelectedVessel(String selectedVessel) { this.selectedVessel = selectedVessel; }

    public double getVesselDraft() { return vesselDraft; }
    public void setVesselDraft(double vesselDraft) { this.vesselDraft = vesselDraft; }

    public double getPortMaxDraft() { return portMaxDraft; }
    public void setPortMaxDraft(double portMaxDraft) { this.portMaxDraft = portMaxDraft; }

    public double getCurrentSpotRate() { return currentSpotRate; }
    public void setCurrentSpotRate(double currentSpotRate) { this.currentSpotRate = currentSpotRate; }

    public double getTargetForecastRate() { return targetForecastRate; }
    public void setTargetForecastRate(double targetForecastRate) { this.targetForecastRate = targetForecastRate; }

    public double getLiveWaveHeight() { return liveWaveHeight; }
    public void setLiveWaveHeight(double liveWaveHeight) { this.liveWaveHeight = liveWaveHeight; }

    public String getWeatherStatus() { return weatherStatus; }
    public void setWeatherStatus(String weatherStatus) { this.weatherStatus = weatherStatus; }

    public int getWaitingVesselsCount() { return waitingVesselsCount; }
    public void setWaitingVesselsCount(int waitingVesselsCount) { this.waitingVesselsCount = waitingVesselsCount; }

    public List<Alert> getActiveAlerts() { return activeAlerts; }
    public void setActiveAlerts(List<Alert> activeAlerts) { this.activeAlerts = activeAlerts; }
}
