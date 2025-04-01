package com.synergisticit.PathfinderAI_ResidenceSevice.domain;

import com.synergisticit.PathfinderAI_ResidenceSevice.domain.Amenities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="hotels")
public class Hotel {

	@Id
	private Integer hotelId;
	private String hotelName;
	private String address;	
	private String city;	
	private String state;
	private Integer starRating;
	private Double averagePrice;
	@ManyToMany
	private Set<Amenities> amenities = new HashSet<>();
	private Double discount;
	private String description;
	private String email;
	private String mobile;
	private String imageURL;	
	private Integer timesBooked;
	
	@OneToMany
	private Set<com.synergisticit.PathfinderAI_ResidenceSevice.domain.HotelRoom> hotelRooms = new HashSet<>();
	
	@Transient
	private Set<String> hotelAmenityNames = new HashSet<>();
	
}
