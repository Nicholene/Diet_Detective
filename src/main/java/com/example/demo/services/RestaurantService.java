package com.example.demo.services;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RestaurantService {

    // Repository for Restaurant
    RestaurantRep restaurantRep;

    // Pattern for validating zip code
    private final Pattern zipCodePattern = Pattern.compile("\\d{5}");

    // Method to validate a new restaurant
    public void validateNewRestaurant(Restaurant restaurant) {
        // Check if the restaurant name is empty
        if (ObjectUtils.isEmpty(restaurant.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Validate the zip code of the restaurant
        validateZipCode(restaurant.getZipCode());

        // Check if a restaurant with the same name and zip code already exists
        Optional<Restaurant> existingRestaurant = restaurantRep.findRestaurantsByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (existingRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Method to validate a zip code
    public void validateZipCode(String zipcode) {
        // Check if the zip code matches the pattern
        if (!zipCodePattern.matcher(zipcode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
