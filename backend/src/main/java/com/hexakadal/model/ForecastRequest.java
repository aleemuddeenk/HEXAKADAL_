package com.hexakadal.model;

public class ForecastRequest {
    private double cargoQuantity = 150000;
    private String originPort = "Hay Point (Australia)";
    private String destinationPort = "Paradip Port";
    private double demurrageRate = 30000.0;
    private int forecastDays = 15;

    public ForecastRequest() {}

    public ForecastRequest(double cargoQuantity, String originPort, String destinationPort, double demurrageRate, int forecastDays) {
        this.cargoQuantity = cargoQuantity;
        this.originPort = originPort;
        this.destinationPort = destinationPort;
        this.demurrageRate = demurrageRate;
        this.forecastDays = forecastDays;
    }

    public double getCargoQuantity() { return cargoQuantity; }
    public void setCargoQuantity(double cargoQuantity) { this.cargoQuantity = cargoQuantity; }

    public String getOriginPort() { return originPort; }
    public void setOriginPort(String originPort) { this.originPort = originPort; }

    public String getDestinationPort() { return destinationPort; }
    public void setDestinationPort(String destinationPort) { this.destinationPort = destinationPort; }

    public double getDemurrageRate() { return demurrageRate; }
    public void setDemurrageRate(double demurrageRate) { this.demurrageRate = demurrageRate; }

    public int getForecastDays() { return forecastDays; }
    public void setForecastDays(int forecastDays) { this.forecastDays = forecastDays; }
}
