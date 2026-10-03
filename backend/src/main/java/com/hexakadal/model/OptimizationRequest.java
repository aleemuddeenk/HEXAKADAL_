package com.hexakadal.model;

public class OptimizationRequest {
    private double cargoQuantity = 150000;
    private String originPort = "Hay Point (Australia)";
    private String destinationPort = "Paradip Port";
    private Double demurrageRateOverride;
    private Double portMaxDraftOverride;

    public OptimizationRequest() {}

    public OptimizationRequest(double cargoQuantity, String originPort, String destinationPort, Double demurrageRateOverride, Double portMaxDraftOverride) {
        this.cargoQuantity = cargoQuantity;
        this.originPort = originPort;
        this.destinationPort = destinationPort;
        this.demurrageRateOverride = demurrageRateOverride;
        this.portMaxDraftOverride = portMaxDraftOverride;
    }

    public double getCargoQuantity() { return cargoQuantity; }
    public void setCargoQuantity(double cargoQuantity) { this.cargoQuantity = cargoQuantity; }

    public String getOriginPort() { return originPort; }
    public void setOriginPort(String originPort) { this.originPort = originPort; }

    public String getDestinationPort() { return destinationPort; }
    public void setDestinationPort(String destinationPort) { this.destinationPort = destinationPort; }

    public Double getDemurrageRateOverride() { return demurrageRateOverride; }
    public void setDemurrageRateOverride(Double demurrageRateOverride) { this.demurrageRateOverride = demurrageRateOverride; }

    public Double getPortMaxDraftOverride() { return portMaxDraftOverride; }
    public void setPortMaxDraftOverride(Double portMaxDraftOverride) { this.portMaxDraftOverride = portMaxDraftOverride; }
}
