package com.hexakadal.controller;

import com.hexakadal.model.OptimizationRequest;
import com.hexakadal.model.OptimizationResponse;
import com.hexakadal.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    public OptimizationController(OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    @PostMapping
    public ResponseEntity<OptimizationResponse> runOptimization(@RequestBody(required = false) OptimizationRequest request) {
        if (request == null) {
            request = new OptimizationRequest();
        }
        return ResponseEntity.ok(optimizationService.runOptimization(request));
    }
}
