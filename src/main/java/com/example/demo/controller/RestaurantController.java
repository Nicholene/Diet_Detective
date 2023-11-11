package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantRep RestaurantRep;
    private final Pattern zipCodePattern = Pattern.compile("\\d{5}");


    public RestaurantController(RestaurantRep RestaurantRep) {
        this.RestaurantRep = RestaurantRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        validateNewRestaurant(restaurant);


        return RestaurantRep.save(restaurant);
    }


    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurant = RestaurantRep.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        }


        throw new ResponseStatusException(HttpStatus.NOT_FOUND);



    @GetMapping
    public Iterable<Restaurant> getAllRestaurants() {
        return RestaurantRep.findAll();
    }


    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy) {
        validateZipCode(zipcode);


        Iterable<Restaurant> restaurants = Collections.EMPTY_LIST;
        if (allergy.equalsIgnoreCase("peanut")) {
            restaurants = RestaurantRep.findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(zipcode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            restaurants = RestaurantRep.findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScore(zipcode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            restaurants = RestaurantRep.findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScore(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        return restaurants;
    }

}
