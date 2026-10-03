package com.hexakadal.service;

import com.hexakadal.model.CongestionData;
import com.hexakadal.model.VesselData;
import com.hexakadal.repository.CsvDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {

    private final CsvDataRepository csvRepository;

    public MarketService(CsvDataRepository csvRepository) {
        this.csvRepository = csvRepository;
    }

    public List<VesselData> getVesselFleet() {
        return csvRepository.getVesselDataList();
    }

    public List<CongestionData> getCongestionData() {
        return csvRepository.getCongestionDataList();
    }
}
