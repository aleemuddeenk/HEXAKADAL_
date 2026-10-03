package com.hexakadal.controller;

import com.hexakadal.model.CongestionData;
import com.hexakadal.model.VesselData;
import com.hexakadal.service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping
    public ResponseEntity<List<VesselData>> getMarketFleet() {
        return ResponseEntity.ok(marketService.getVesselFleet());
    }

    @GetMapping("/fleet")
    public ResponseEntity<List<VesselData>> getFleet() {
        return ResponseEntity.ok(marketService.getVesselFleet());
    }

    @GetMapping("/congestion")
    public ResponseEntity<List<CongestionData>> getCongestion() {
        return ResponseEntity.ok(marketService.getCongestionData());
    }
}
