package com.hexakadal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FreightData {
    private String date;
    private double price;
    private double open;
    private double high;
    private double low;
    private String volume;

    @JsonProperty("changePercent")
    private String changePercent;

    public FreightData() {}

    public FreightData(String date, double price, double open, double high, double low, String volume, String changePercent) {
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.changePercent = changePercent;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getOpen() { return open; }
    public void setOpen(double open) { this.open = open; }

    public double getHigh() { return high; }
    public void setHigh(double high) { this.high = high; }

    public double getLow() { return low; }
    public void setLow(double low) { this.low = low; }

    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }

    public String getChangePercent() { return changePercent; }
    public void setChangePercent(String changePercent) { this.changePercent = changePercent; }
}
