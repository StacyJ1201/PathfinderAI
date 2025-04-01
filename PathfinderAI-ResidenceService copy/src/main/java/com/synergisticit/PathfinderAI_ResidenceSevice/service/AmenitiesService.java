package com.synergisticit.PathfinderAI_ResidenceSevice.service;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Amenities;

import java.util.List;
import java.util.Optional;

public interface AmenitiesService {
    Amenities saveAmenities(Amenities amenities);
    List<Amenities> findAllAmenities();
    Optional<Amenities> findAmenitiesById(int id);
    Amenities updateAmenitiesById(int id, Amenities amenities);
    void deleteAmenitiesById(int id);
}
