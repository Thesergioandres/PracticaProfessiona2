package com.example.clasesabado.controller;

import com.example.clasesabado.Service.impl.PropertyService;
import com.example.clasesabado.dto.ListPropertyResponse;
import com.example.clasesabado.entity.PropertyEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permitir solicitudes desde el origen del frontend
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/create")
    public ResponseEntity<PropertyEntity> createProperty(@RequestBody PropertyEntity property) {
        PropertyEntity savedProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(savedProperty);
    }

    @GetMapping("/get")
    public ResponseEntity<ListPropertyResponse> getProperties(@RequestParam double minPrice, @RequestParam double maxPrice) {
        ListPropertyResponse response = propertyService.getProperties(minPrice, maxPrice);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyEntity> updateProperty(@PathVariable Long id, @RequestBody PropertyEntity property) {
        property.setId(id);
        PropertyEntity updatedProperty = propertyService.updateProperty(property);
        return ResponseEntity.ok(updatedProperty);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok("Property deleted (under logic) successfully.");
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enableProperty(@PathVariable Long id) {
        propertyService.enableProperty(id);
        return ResponseEntity.ok("Property enabled successfully.");
    }


    @PostMapping("/{id}/rent")
    public ResponseEntity<String> rentProperty(@PathVariable Long id) {
        propertyService.rentProperty(id);
        return ResponseEntity.ok("Property leased successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyEntity>> getAllProperties() {
        List<PropertyEntity> properties = propertyService.findAllProperties();
        return ResponseEntity.ok(properties);
    }



}