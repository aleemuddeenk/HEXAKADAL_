package com.hexakadal.controller;

import com.hexakadal.model.WeatherData;
import com.hexakadal.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherData> getWeather(
            @RequestParam(defaultValue = "Paradip Port") String portName,
            @RequestParam(defaultValue = "16.5") double vesselDraft) {
        return ResponseEntity.ok(weatherService.getPortWeatherTelemetry(portName, vesselDraft));
    }

    @GetMapping("/{portName}")
    public ResponseEntity<WeatherData> getWeatherForPort(
            @PathVariable String portName,
            @RequestParam(defaultValue = "16.5") double vesselDraft) {
        return ResponseEntity.ok(weatherService.getPortWeatherTelemetry(portName, vesselDraft));
    }
}
