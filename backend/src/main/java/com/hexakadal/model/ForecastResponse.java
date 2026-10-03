package com.hexakadal.model;

import java.util.List;

public class ForecastResponse {
    private double currentSpotRate;
    private double forecastSpotRate;
    private double rateDelta;
    private double percentageChange;
    private double confidenceScore;
    private String modelUsed;
    private String xaiExplanation;
    private List<HistoricalPoint> forecastTrend;

    public ForecastResponse() {}

    public ForecastResponse(double currentSpotRate, double forecastSpotRate, double rateDelta, double percentageChange, double confidenceScore, String modelUsed, String xaiExplanation, List<HistoricalPoint> forecastTrend) {
        this.currentSpotRate = currentSpotRate;
        this.forecastSpotRate = forecastSpotRate;
        this.rateDelta = rateDelta;
        this.percentageChange = percentageChange;
        this.confidenceScore = confidenceScore;
        this.modelUsed = modelUsed;
        this.xaiExplanation = xaiExplanation;
        this.forecastTrend = forecastTrend;
    }

    public double getCurrentSpotRate() { return currentSpotRate; }
    public void setCurrentSpotRate(double currentSpotRate) { this.currentSpotRate = currentSpotRate; }

    public double getForecastSpotRate() { return forecastSpotRate; }
    public void setForecastSpotRate(double forecastSpotRate) { this.forecastSpotRate = forecastSpotRate; }

    public double getRateDelta() { return rateDelta; }
    public void setRateDelta(double rateDelta) { this.rateDelta = rateDelta; }

    public double getPercentageChange() { return percentageChange; }
    public void setPercentageChange(double percentageChange) { this.percentageChange = percentageChange; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getModelUsed() { return modelUsed; }
    public void setModelUsed(String modelUsed) { this.modelUsed = modelUsed; }

    public String getXaiExplanation() { return xaiExplanation; }
    public void setXaiExplanation(String xaiExplanation) { this.xaiExplanation = xaiExplanation; }

    public List<HistoricalPoint> getForecastTrend() { return forecastTrend; }
    public void setForecastTrend(List<HistoricalPoint> forecastTrend) { this.forecastTrend = forecastTrend; }

    public static class HistoricalPoint {
        private String label;
        private double value;

        public HistoricalPoint() {}
        public HistoricalPoint(String label, double value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }
    }
}
