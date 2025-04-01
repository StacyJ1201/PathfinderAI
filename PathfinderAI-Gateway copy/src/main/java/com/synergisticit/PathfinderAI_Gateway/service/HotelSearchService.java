package com.synergisticit.PathfinderAI_Gateway.service;

import com.synergisticit.PathfinderAI_Gateway.dto.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@Service
public class HotelSearchService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${residence.service.url}")
    private String residenceMSUrl;

    public List<Hotel> searchHotels(String city, Double maxPrice, Integer starRating, Set<String> amenities){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(residenceMSUrl + "/api/hotels")
                .queryParam("city", city)
                .queryParam("maxPrice", maxPrice)
                .queryParam("starRating", starRating);

        if(amenities != null && !amenities.isEmpty()){
            builder.queryParam("amenities", String.join(", ", amenities));
        }

        ResponseEntity<List<Hotel>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Hotel>>() {}
        );

        List<Hotel> hotels = response.getBody();

        if(hotels != null) {
            hotels.forEach(hotel -> {
                if(hotel.getImageURL() != null && !hotel.getImageURL().isEmpty()){
                    hotel.setImageURL(residenceMSUrl + hotel.getImageURL());
                }
            });
        }

        return response.getBody();
    }

    public Hotel getHotelById(Integer id){
        String url = residenceMSUrl + "/api/hotels/" + id;
        return restTemplate.getForObject(url, Hotel.class);
    }

    public List<Hotel> findAllHotels(){
        String url = residenceMSUrl + "/api/hotels";
        ResponseEntity<List<Hotel>> response =  restTemplate.exchange (
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Hotel>>() {}
        );

        List<Hotel> hotels = response.getBody();

        if(hotels != null) {
            hotels.forEach(hotel -> {
                if(hotel.getImageURL() != null && !hotel.getImageURL().isEmpty()){
                    hotel.setImageURL(residenceMSUrl + hotel.getImageURL());
                }
            });
        }

        return response.getBody();
    }
}
