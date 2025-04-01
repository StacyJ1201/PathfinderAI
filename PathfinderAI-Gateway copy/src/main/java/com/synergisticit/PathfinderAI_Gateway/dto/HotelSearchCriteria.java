package com.synergisticit.PathfinderAI_Gateway.dto;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class HotelSearchCriteria {
    private String location;
    private String checkIn;
    private String checkOut;
    private Integer guests;
    private Double maxPrice;
    private Integer starRating;
    private Set<String> amenities = new HashSet<>();
}
