package com.hexakadal.model;

public class VesselData {
    private String companyName;
    private String shipName;
    private int builtYear;
    private long gt;
    private long dwt;
    private double length;
    private double width;
    private double estimatedDraft;

    public VesselData() {}

    public VesselData(String companyName, String shipName, int builtYear, long gt, long dwt, double length, double width, double estimatedDraft) {
        this.companyName = companyName;
        this.shipName = shipName;
        this.builtYear = builtYear;
        this.gt = gt;
        this.dwt = dwt;
        this.length = length;
        this.width = width;
        this.estimatedDraft = estimatedDraft;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public int getBuiltYear() { return builtYear; }
    public void setBuiltYear(int builtYear) { this.builtYear = builtYear; }

    public long getGt() { return gt; }
    public void setGt(long gt) { this.gt = gt; }

    public long getDwt() { return dwt; }
    public void setDwt(long dwt) { this.dwt = dwt; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getEstimatedDraft() { return estimatedDraft; }
    public void setEstimatedDraft(double estimatedDraft) { this.estimatedDraft = estimatedDraft; }
}
