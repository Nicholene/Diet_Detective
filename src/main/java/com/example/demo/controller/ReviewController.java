package com.example.demo.controller;

import com.example.demo.model.DiningReview;
import com.example.demo.model.Restaurant;
import com.example.demo.model.ReviewStatus;
import com.example.demo.repository.DiningReviewRep;
import com.example.demo.repository.RestaurantRep;
import com.example.demo.repository.UserRep;
import com.example.demo.services.RestaurantService;
import com.example.demo.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.text.DecimalFormat;
import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class ReviewController {
    private final DiningReviewRep diningReviewRep;
    private final RestaurantRep restaurantRep;
    ReviewService reviewService;

    // Constructor for the ReviewController
    public ReviewController(DiningReviewRep diningReviewRep, RestaurantRep restaurantRep) {
        this.diningReviewRep = diningReviewRep;
        this.restaurantRep = restaurantRep;
    }

    // Method to add user review
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReview(@RequestBody DiningReview review) {
        reviewService.validateUserReview(review);

        // Check if the restaurant exists
        Optional<Restaurant> optionalRestaurant = restaurantRep.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Set the review status and save the review
        review.setStatus(ReviewStatus.PENDING);
        diningReviewRep.save(review);
    }


}
