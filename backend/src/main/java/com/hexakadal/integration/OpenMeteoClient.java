package com.hexakadal.integration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OpenMeteoClient {

    private static final Logger log = LoggerFactory.getLogger(OpenMeteoClient.class);
    private final RestTemplate restTemplate;

    public OpenMeteoClient() {
        this.restTemplate = new RestTemplate();
    }

    @CircuitBreaker(name = "marineApi", fallbackMethod = "fallbackWaveHeight")
    @Retry(name = "marineApi", fallbackMethod = "fallbackWaveHeight")
    public double fetchLiveWaveHeight(double latitude, double longitude) {
        String url = String.format("https://marine-api.open-meteo.com/v1/marine?latitude=%.2f&longitude=%.2f&current=wave_height", latitude, longitude);
        log.info("Fetching real-time marine telemetry from Open-Meteo: {}", url);
        
        try {
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("current")) {
                Map<?, ?> current = (Map<?, ?>) response.get("current");
                if (current != null && current.containsKey("wave_height")) {
                    Object val = current.get("wave_height");
                    if (val instanceof Number) {
                        return ((Number) val).doubleValue();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to fetch live wave height: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return 1.2; // default moderate swell fallback
    }

    public double fallbackWaveHeight(double latitude, double longitude, Throwable t) {
        log.warn("Resilience4j Fallback triggered for marine API ({}, {}). Reason: {}", latitude, longitude, t.getMessage());
        return 1.4; // Safe cached fallback value
    }
}
