package com.hexakadal.model;

public class Alert {
    private String id;
    private String title;
    private String description;
    private String severity; // CRITICAL, WARNING, INFO, SUCCESS
    private String category; // WEATHER, DRAFT, ML_FORECAST, OPTIMIZATION
    private String timestamp;

    public Alert() {}

    public Alert(String id, String title, String description, String severity, String category, String timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.category = category;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
