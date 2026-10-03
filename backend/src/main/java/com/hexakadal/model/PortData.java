package com.hexakadal.model;

public class PortData {
    private String country;
    private String portName;
    private String locode;
    private double maxDraft;
    private double latitude;
    private double longitude;

    public PortData() {}

    public PortData(String country, String portName, String locode, double maxDraft, double latitude, double longitude) {
        this.country = country;
        this.portName = portName;
        this.locode = locode;
        this.maxDraft = maxDraft;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }

    public String getLocode() { return locode; }
    public void setLocode(String locode) { this.locode = locode; }

    public double getMaxDraft() { return maxDraft; }
    public void setMaxDraft(double maxDraft) { this.maxDraft = maxDraft; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
