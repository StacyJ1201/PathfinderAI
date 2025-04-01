package com.synergisticit.PathfinderAI_ResidenceSevice.controller;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Amenities;
import com.synergisticit.PathfinderAI_ResidenceSevice.service.AmenitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenitiesController {

    @Autowired
    AmenitiesService amenitiesService;

    @GetMapping()
    public ResponseEntity<List<Amenities>> findAllAmenities(){
        List<Amenities> allAmenities = amenitiesService.findAllAmenities();
        return ResponseEntity.ok(allAmenities);
    }

    @PostMapping()
    public ResponseEntity<Amenities> saveAmenities(@RequestBody Amenities amenities){
        Amenities createdAmenities = amenitiesService.saveAmenities(amenities);
        return ResponseEntity.ok(createdAmenities);
    }
}
