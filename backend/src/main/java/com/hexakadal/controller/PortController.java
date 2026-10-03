package com.hexakadal.controller;

import com.hexakadal.model.PortData;
import com.hexakadal.service.PortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ports")
public class PortController {

    private final PortService portService;

    public PortController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping
    public ResponseEntity<List<PortData>> getPorts() {
        return ResponseEntity.ok(portService.getAllPorts());
    }

    @GetMapping("/{name}")
    public ResponseEntity<PortData> getPortByName(@PathVariable String name) {
        return ResponseEntity.ok(portService.getPortByName(name));
    }
}
