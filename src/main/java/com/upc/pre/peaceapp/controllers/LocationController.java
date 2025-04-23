package com.upc.pre.peaceapp.controllers;

import com.upc.pre.peaceapp.schemas.LocationSchema;
import com.upc.pre.peaceapp.services.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Location", description = "Location Endpoints")
public class LocationController {
    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveNewLocation(@RequestBody LocationSchema location) {
        try {
            return ResponseEntity.created(null).body(service.saveLocation(location));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllLocations() {
        try{
            return ResponseEntity.ok(service.getAllLocations());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    // GET /api/v1/locations/dangerous?limit=10
    @GetMapping("/dangerous")
    public ResponseEntity<?> getDangerousLocations(@PathParam("quantity_reports") int quantityReports) {
        try{
            return ResponseEntity.ok(service.obtainDangerousLocations(quantityReports));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
