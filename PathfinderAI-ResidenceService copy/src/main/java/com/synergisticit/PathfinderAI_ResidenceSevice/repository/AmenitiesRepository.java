package com.synergisticit.PathfinderAI_ResidenceSevice.repository;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {
}
