package com.hexakadal.model;

public class CongestionData {
    private String economyLabel;
    private String commercialMarketLabel;
    private double medianTimeInPortDays;
    private double averageAgeYears;
    private double averageCargoDwt;
    private String period;

    public CongestionData() {}

    public CongestionData(String economyLabel, String commercialMarketLabel, double medianTimeInPortDays, double averageAgeYears, double averageCargoDwt, String period) {
        this.economyLabel = economyLabel;
        this.commercialMarketLabel = commercialMarketLabel;
        this.medianTimeInPortDays = medianTimeInPortDays;
        this.averageAgeYears = averageAgeYears;
        this.averageCargoDwt = averageCargoDwt;
        this.period = period;
    }

    public String getEconomyLabel() { return economyLabel; }
    public void setEconomyLabel(String economyLabel) { this.economyLabel = economyLabel; }

    public String getCommercialMarketLabel() { return commercialMarketLabel; }
    public void setCommercialMarketLabel(String commercialMarketLabel) { this.commercialMarketLabel = commercialMarketLabel; }

    public double getMedianTimeInPortDays() { return medianTimeInPortDays; }
    public void setMedianTimeInPortDays(double medianTimeInPortDays) { this.medianTimeInPortDays = medianTimeInPortDays; }

    public double getAverageAgeYears() { return averageAgeYears; }
    public void setAverageAgeYears(double averageAgeYears) { this.averageAgeYears = averageAgeYears; }

    public double getAverageCargoDwt() { return averageCargoDwt; }
    public void setAverageCargoDwt(double averageCargoDwt) { this.averageCargoDwt = averageCargoDwt; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
