package com.hexakadal.controller;

import com.hexakadal.model.ForecastRequest;
import com.hexakadal.model.ForecastResponse;
import com.hexakadal.model.FreightData;
import com.hexakadal.service.FreightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    private final FreightService freightService;

    public ForecastController(FreightService freightService) {
        this.freightService = freightService;
    }

    @PostMapping
    public ResponseEntity<ForecastResponse> getForecast(@RequestBody(required = false) ForecastRequest request) {
        if (request == null) {
            request = new ForecastRequest();
        }
        return ResponseEntity.ok(freightService.calculateForecast(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<FreightData>> getHistoricalRates() {
        return ResponseEntity.ok(freightService.getHistoricalFreightData());
    }
}
