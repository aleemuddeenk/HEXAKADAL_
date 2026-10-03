package com.hexakadal.service;

import com.hexakadal.integration.OpenMeteoClient;
import com.hexakadal.model.PortData;
import com.hexakadal.model.WeatherData;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final OpenMeteoClient openMeteoClient;
    private final PortService portService;

    public WeatherService(OpenMeteoClient openMeteoClient, PortService portService) {
        this.openMeteoClient = openMeteoClient;
        this.portService = portService;
    }

    public WeatherData getPortWeatherTelemetry(String portName, double vesselDraft) {
        PortData port = portService.getPortByName(portName);
        double waveHeight = openMeteoClient.fetchLiveWaveHeight(port.getLatitude(), port.getLongitude());

        String status;
        double weatherMult;
        if (waveHeight >= 3.0) {
            status = "Cyclone Alert / Port Halt";
            weatherMult = 2.5;
        } else if (waveHeight >= 1.8) {
            status = "Heavy Swell / Delay Risk";
            weatherMult = 1.4;
        } else {
            status = "Clear / Safe Sea State";
            weatherMult = 1.0;
        }

        double baseQueueHours = 36.0;
        double depthMargin = port.getMaxDraft() - vesselDraft;
        double tidalDelayHours = depthMargin < 0.5 ? 6.0 : 0.0;

        double totalQueueHours = Math.round((baseQueueHours * weatherMult + tidalDelayHours) * 10.0) / 10.0;
        int waitingVessels = Math.max(1, (int) Math.round(totalQueueHours / 2.8));

        return new WeatherData(
            port.getPortName(),
            waveHeight,
            status,
            weatherMult,
            tidalDelayHours,
            baseQueueHours,
            totalQueueHours,
            waitingVessels
        );
    }
}
