package com.synergisticit.PathfinderAI_ResidenceSevice.service;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Amenities;
import com.synergisticit.PathfinderAI_ResidenceSevice.repository.AmenitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenitiesServiceImpl implements AmenitiesService {

    @Autowired
    AmenitiesRepository amenitiesRepository;

    @Override
    public Amenities saveAmenities(Amenities amenities) {
        return amenitiesRepository.save(amenities);
    }

    @Override
    public List<Amenities> findAllAmenities() {
        return amenitiesRepository.findAll();
    }

    @Override
    public Optional<Amenities> findAmenitiesById(int id) {
        return amenitiesRepository.findById(id);
    }

    @Override
    public Amenities updateAmenitiesById(int id, Amenities amenities) {
        Amenities foundAmenities = findAmenitiesById(id).get();

        foundAmenities.setA_id(id);
        foundAmenities.setName(amenities.getName());

        return amenitiesRepository.save(foundAmenities);
    }

    @Override
    public void deleteAmenitiesById(int id) {
        amenitiesRepository.deleteById(id);
    }
}
