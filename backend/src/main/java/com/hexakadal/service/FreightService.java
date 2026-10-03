package com.hexakadal.service;

import com.hexakadal.ml.XGBoostFreightForecaster;
import com.hexakadal.model.ForecastRequest;
import com.hexakadal.model.ForecastResponse;
import com.hexakadal.model.FreightData;
import com.hexakadal.repository.CsvDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightService {

    private final CsvDataRepository csvRepository;
    private final XGBoostFreightForecaster xgbForecaster;

    public FreightService(CsvDataRepository csvRepository, XGBoostFreightForecaster xgbForecaster) {
        this.csvRepository = csvRepository;
        this.xgbForecaster = xgbForecaster;
    }

    public List<FreightData> getHistoricalFreightData() {
        return csvRepository.getFreightDataList();
    }

    public double getCurrentSpotRate() {
        List<FreightData> data = csvRepository.getFreightDataList();
        if (data != null && !data.isEmpty()) {
            return data.get(0).getPrice();
        }
        return 18.50; // default fallback spot rate
    }

    public ForecastResponse calculateForecast(ForecastRequest request) {
        double spotRate = getCurrentSpotRate();
        List<FreightData> history = csvRepository.getFreightDataList();
        return xgbForecaster.predict15DayFreightRate(spotRate, history, request.getForecastDays());
    }
}
