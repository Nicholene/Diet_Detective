package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRep;
import com.example.demo.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestaurantController {

    RestaurantService restaurantService;

    private final RestaurantRep restaurantRep;



    public RestaurantController(RestaurantRep restaurantRep) {
        this.restaurantRep = restaurantRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.validateNewRestaurant(restaurant);


        return restaurantRep.save(restaurant);
    }


    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantRep.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        }


        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @GetMapping
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRep.findAll();
    }


    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy) {
        restaurantService.validateZipCode(zipcode);


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
