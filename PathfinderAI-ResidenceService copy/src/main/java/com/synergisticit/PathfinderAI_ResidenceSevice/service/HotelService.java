package com.synergisticit.PathfinderAI_ResidenceSevice.service;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Hotel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HotelService {

    List<Hotel> findAllHotels();
    Optional<Hotel> findHotelById(Integer id);
    List<Hotel> findHotelsByCity(String city);
    Hotel saveHotel(Hotel hotel);
    void deleteHotelById(Integer id);
    List<Hotel> searchHotels(String city, Double maxPrice, Integer starRating, Set<String> amenities);
}
