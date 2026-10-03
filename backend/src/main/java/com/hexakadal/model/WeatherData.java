package com.hexakadal.model;

public class WeatherData {
    private String portName;
    private double waveHeight;
    private String weatherStatus;
    private double weatherMultiplier;
    private double tidalDelayHours;
    private double baseQueueHours;
    private double totalAnchorageQueueHours;
    private int waitingVessels;

    public WeatherData() {}

    public WeatherData(String portName, double waveHeight, String weatherStatus, double weatherMultiplier, double tidalDelayHours, double baseQueueHours, double totalAnchorageQueueHours, int waitingVessels) {
        this.portName = portName;
        this.waveHeight = waveHeight;
        this.weatherStatus = weatherStatus;
        this.weatherMultiplier = weatherMultiplier;
        this.tidalDelayHours = tidalDelayHours;
        this.baseQueueHours = baseQueueHours;
        this.totalAnchorageQueueHours = totalAnchorageQueueHours;
        this.waitingVessels = waitingVessels;
    }

    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }

    public double getWaveHeight() { return waveHeight; }
    public void setWaveHeight(double waveHeight) { this.waveHeight = waveHeight; }

    public String getWeatherStatus() { return weatherStatus; }
    public void setWeatherStatus(String weatherStatus) { this.weatherStatus = weatherStatus; }

    public double getWeatherMultiplier() { return weatherMultiplier; }
    public void setWeatherMultiplier(double weatherMultiplier) { this.weatherMultiplier = weatherMultiplier; }

    public double getTidalDelayHours() { return tidalDelayHours; }
    public void setTidalDelayHours(double tidalDelayHours) { this.tidalDelayHours = tidalDelayHours; }

    public double getBaseQueueHours() { return baseQueueHours; }
    public void setBaseQueueHours(double baseQueueHours) { this.baseQueueHours = baseQueueHours; }

    public double getTotalAnchorageQueueHours() { return totalAnchorageQueueHours; }
    public void setTotalAnchorageQueueHours(double totalAnchorageQueueHours) { this.totalAnchorageQueueHours = totalAnchorageQueueHours; }

    public int getWaitingVessels() { return waitingVessels; }
    public void setWaitingVessels(int waitingVessels) { this.waitingVessels = waitingVessels; }
}
