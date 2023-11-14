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
    private final UserRep userRep;
    private final RestaurantRep restaurantRep;

    ReviewService reviewService;

    public ReviewController(DiningReviewRep diningReviewRep, UserRep userRep, RestaurantRep restaurantRep) {
        this.diningReviewRep = diningReviewRep;
        this.userRep = userRep;
        this.restaurantRep = restaurantRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReview(@RequestBody DiningReview review) {
        reviewService.validateUserReview(review);


        Optional<Restaurant> optionalRestaurant = restaurantRep.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        review.setStatus(ReviewStatus.PENDING);
        diningReviewRep.save(review);
    }


}
