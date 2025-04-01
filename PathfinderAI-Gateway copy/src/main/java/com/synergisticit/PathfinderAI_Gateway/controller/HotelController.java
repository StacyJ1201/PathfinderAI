package com.synergisticit.PathfinderAI_Gateway.controller;

import com.synergisticit.PathfinderAI_Gateway.dto.Hotel;
import com.synergisticit.PathfinderAI_Gateway.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class HotelController {

    @Autowired
    HotelSearchService hotelSearchService;

    @GetMapping("/home")
    public String showHomePage(Model model){
        model.addAttribute("hotels", hotelSearchService.findAllHotels());
        return "home";
    }


    @GetMapping("/search/hotels")
    public String findHotelsBySearch(@RequestParam (required = false) String city,
                                     @RequestParam (required = false) Set<String> amenities,
                                     @RequestParam (required = false) Integer starRating,
                                     @RequestParam (required = false) Double maxPrice,
                                     Model model){

        List<Hotel> hotels = hotelSearchService.searchHotels(city, maxPrice, starRating, amenities);

        model.addAttribute("hotels", hotels);

        model.addAttribute("searchLocation", city);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("starRating", starRating);
        model.addAttribute("amenities", amenities);

        return "home";
    }
}
