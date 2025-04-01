package com.synergisticit.PathfinderAI_Gateway.dto;

import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Hotel {
    private Integer hotelId;
    private String hotelName;
    private String address;
    private String city;
    private String state;
    private Integer starRating;
    private Double averagePrice;
    private Double discount;
    private Set<String> amenities = new HashSet<>();
    private String imageURL;
}
