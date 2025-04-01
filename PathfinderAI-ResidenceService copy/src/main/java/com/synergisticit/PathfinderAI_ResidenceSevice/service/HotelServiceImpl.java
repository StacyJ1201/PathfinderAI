package com.synergisticit.PathfinderAI_ResidenceSevice.service;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Hotel;
import com.synergisticit.PathfinderAI_ResidenceSevice.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    HotelRepository hotelRepository;


    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> findHotelById(Integer id) {
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        return hotelRepository.findHotelsByCity(city);
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotelById(Integer id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> searchHotels(String city,
                                    Double maxPrice,
                                    Integer starRating,
                                    Set<String> amenities) {

        List<Hotel> hotels = hotelRepository.findAll();

        if(city != null && !city.isEmpty()){
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getCity().toLowerCase()
                            .contains(city.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if(maxPrice != null){
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getAveragePrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        if(starRating != null){
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getStarRating() >= starRating)
                    .collect(Collectors.toList());
        }

        if(amenities != null && !amenities.isEmpty()){
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getAmenities()
                            .containsAll(amenities))
                    .collect(Collectors.toList());
        }

        return hotels;
    }
}
