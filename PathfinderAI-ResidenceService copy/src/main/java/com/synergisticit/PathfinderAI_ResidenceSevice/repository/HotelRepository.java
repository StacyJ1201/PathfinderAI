package com.synergisticit.PathfinderAI_ResidenceSevice.repository;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findHotelsByCity(String city);

}
