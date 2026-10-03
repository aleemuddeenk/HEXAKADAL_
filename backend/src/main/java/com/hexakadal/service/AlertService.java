package com.hexakadal.service;

import com.hexakadal.model.Alert;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    public List<Alert> getActiveAlerts() {
        List<Alert> alerts = new ArrayList<>();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        alerts.add(new Alert(
            "ALT-001",
            "XGBoost Rate Drop Signal",
            "Freight spot rate forecasted to drop from $18.50/MT to $16.28/MT (-12.0%) over 15-day horizon.",
            "INFO",
            "ML_FORECAST",
            now
        ));

        alerts.add(new Alert(
            "ALT-002",
            "Moderate Swell Advisory",
            "Live wave height at Paradip Port recorded at 2.4m. Operational delay multiplier applied: +1.4x.",
            "WARNING",
            "WEATHER",
            now
        ));

        alerts.add(new Alert(
            "ALT-003",
            "Draft Clearance Notice",
            "Capesize vessel draft (16.5m) within safe operational margin for Paradip berth limit (17.5m).",
            "SUCCESS",
            "DRAFT",
            now
        ));

        alerts.add(new Alert(
            "ALT-004",
            "OR-Tools Executive Decision",
            "Net Decision Value (NDV) calculated at +$307,300 USD. Recommendation: DEFER FIXING CHARTERPARTY.",
            "CRITICAL",
            "OPTIMIZATION",
            now
        ));

        return alerts;
    }
}
