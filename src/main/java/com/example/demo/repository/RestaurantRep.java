package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
public interface RestaurantRep extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantsByNameAndZipCode(String name, String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndPeanutRatingNotNullOrderByPeanutRating(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndDairyRatingNotNullOrderByDairyRating(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndEggRatingNotNullOrderByEggRating(String zipcode);
}

