package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.text.DecimalFormat;

@RequestMapping("/admin")
@RestController
public class ReviewController {
    private final DiningReviewRep DiningReviewRep;
    private final UserRep UserRep;
    private final RestaurantRep RestaurantRep;


    public ReviewController(DiningReviewRep DiningReviewRep, UserRep UserRep, RestaurantRep RestaurantRep) {
        this.DiningReviewRep = DiningReviewRep;
        this.UserRep = UserRep;
        this.RestaurantRep = RestaurantRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReview(@RequestBody Review review) {
        validateUserReview(review);


        Optional<Restaurant> optionalRestaurant = RestaurantRep.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        review.setStatus(ReviewStatus.PENDING);
        DiningReviewRep.save(review);
    }


}
