package com.synergisticit.PathfinderAI_ResidenceSevice.controller;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Hotel;
import com.synergisticit.PathfinderAI_ResidenceSevice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @GetMapping()
    public ResponseEntity<List<Hotel>> findAllHotels(@RequestParam (required = false) String city,
                                                     @RequestParam (required = false) Integer starRating,
                                                     @RequestParam (required = false) Double maxPrice,
                                                     @RequestParam (required = false) String amenities){

        Set<String> amenitySet = null;
        if(amenities != null && !amenities.trim().isEmpty()){
            amenitySet = Arrays.stream(amenities.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }

        try {
            List<Hotel> hotels = hotelService.searchHotels(city, maxPrice, starRating, amenitySet);

            if(!hotels.isEmpty()){
                return ResponseEntity.ok(hotels);
            } else {
                return ResponseEntity.noContent().build();
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        Hotel createdHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(createdHotel);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> findHotel(@PathVariable Integer hotelId){
        Hotel foundHotel = hotelService.findHotelById(hotelId).get();
        return ResponseEntity.ok(foundHotel);
    }


}
