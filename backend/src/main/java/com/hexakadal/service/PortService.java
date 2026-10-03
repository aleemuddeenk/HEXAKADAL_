package com.hexakadal.service;

import com.hexakadal.model.PortData;
import com.hexakadal.repository.CsvDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {

    private final CsvDataRepository csvRepository;

    public PortService(CsvDataRepository csvRepository) {
        this.csvRepository = csvRepository;
    }

    public List<PortData> getAllPorts() {
        return csvRepository.getPortDataList();
    }

    public PortData getPortByName(String name) {
        return csvRepository.getPortDataList().stream()
                .filter(p -> p.getPortName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> new PortData("India", name, "INPRT", 17.50, 20.26, 86.67));
    }
}
