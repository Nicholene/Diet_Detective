package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRep;
import com.example.demo.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestaurantController {

    // Declare the service and repository as final
    private final RestaurantRep restaurantRep;
    private final RestaurantService restaurantService;

    // Initialize the service and repository in the constructor
    public RestaurantController(RestaurantRep restaurantRep, RestaurantService restaurantService) {
        this.restaurantRep = restaurantRep;
        this.restaurantService = restaurantService;
    }

    // Endpoint to add a new restaurant
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        // Validate the new restaurant
        restaurantService.validateNewRestaurant(restaurant);

        // Save the new restaurant and return it
        return restaurantRep.save(restaurant);
    }

    // Endpoint to get a restaurant by id
    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        // Find the restaurant by id or throw an exception if not found
        return restaurantRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Endpoint to get all restaurants
    @GetMapping
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRep.findAll();
    }

    // Endpoint to search restaurants by zipcode and allergy
    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy) {
        // Validate the zipcode
        restaurantService.validateZipCode(zipcode);

        // Find the restaurants based on the allergy and zipcode
        Iterable<Restaurant> restaurants = Collections.EMPTY_LIST;
        if (allergy.equalsIgnoreCase("peanut")) {
            restaurants = restaurantRep.findRestaurantsByZipCodeAndPeanutRatingNotNullOrderByPeanutRating(zipcode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            restaurants = restaurantRep.findRestaurantsByZipCodeAndDairyRatingNotNullOrderByDairyRating(zipcode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            restaurants = restaurantRep.findRestaurantsByZipCodeAndEggRatingNotNullOrderByEggRating(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return restaurants;
    }
}
