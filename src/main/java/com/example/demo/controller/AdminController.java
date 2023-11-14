package com.example.demo.controller;

import com.example.demo.model.AdminReview;
import com.example.demo.model.DiningReview;
import com.example.demo.model.Restaurant;
import com.example.demo.model.ReviewStatus;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.DiningReviewRep;
import com.example.demo.repository.UserRep;
import com.example.demo.repository.RestaurantRep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@RequestMapping("/admin")
@RestController
public class AdminController {

    AdminService adminService;
    private final DiningReviewRep diningReviewRep;
    private final UserRep userRep;
    private final RestaurantRep restaurantRep;





    public AdminController(DiningReviewRep diningReviewRep, UserRep userRep, RestaurantRep restaurantRep) {
        this.diningReviewRep = diningReviewRep;
        this.userRep = userRep;
        this.restaurantRep = restaurantRep;
    }


    @GetMapping("/reviews")
    public List<DiningReview> getReviewsByStatus(@RequestParam String review_status) {
        ReviewStatus reviewStatus = ReviewStatus.PENDING;
        try {
            reviewStatus = ReviewStatus.valueOf(review_status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return diningReviewRep.findDiningReviewsByStatus(reviewStatus);
    }

    @PutMapping("/reviews/{reviewId}")
    public void performReviewAction(@PathVariable Long reviewId, @RequestBody AdminReview adminReviewAction) {
        Optional<DiningReview> optionalReview = diningReviewRep.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        DiningReview review = optionalReview.get();


        Optional<Restaurant> optionalRestaurant = restaurantRep.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        if (adminReviewAction.getAdminApproved()) {
            review.setStatus(ReviewStatus.ACCEPTED);
        } else {
            review.setStatus(ReviewStatus.REJECTED);
        }


        diningReviewRep.save(review);
        adminService.updateRestaurantReviewScores(optionalRestaurant.get());
    }

}
