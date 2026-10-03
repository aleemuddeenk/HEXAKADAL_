package com.hexakadal.service;

import com.hexakadal.model.ForecastRequest;
import com.hexakadal.model.ForecastResponse;
import com.hexakadal.model.OptimizationRequest;
import com.hexakadal.model.OptimizationResponse;
import com.hexakadal.model.WeatherData;
import com.hexakadal.optimization.VesselRoutingOptimizer;
import org.springframework.stereotype.Service;

@Service
public class OptimizationService {

    private final VesselRoutingOptimizer optimizer;
    private final FreightService freightService;
    private final WeatherService weatherService;

    public OptimizationService(VesselRoutingOptimizer optimizer, FreightService freightService, WeatherService weatherService) {
        this.optimizer = optimizer;
        this.freightService = freightService;
        this.weatherService = weatherService;
    }

    public OptimizationResponse runOptimization(OptimizationRequest request) {
        ForecastResponse forecast = freightService.calculateForecast(
            new ForecastRequest(request.getCargoQuantity(), request.getOriginPort(), request.getDestinationPort(), 30000.0, 15)
        );

        WeatherData weather = weatherService.getPortWeatherTelemetry(request.getDestinationPort(), 16.5);

        return optimizer.optimizeVesselChartering(
            request,
            forecast.getCurrentSpotRate(),
            forecast.getForecastSpotRate(),
            weather.getTotalAnchorageQueueHours()
        );
    }
}
