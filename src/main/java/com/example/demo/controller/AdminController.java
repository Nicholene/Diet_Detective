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

import java.util.List;
import java.util.Optional;

// Define the request mapping for the controller
@RequestMapping("/admin")
@RestController
public class AdminController {

    // Declare the repositories and service as final
    private final DiningReviewRep diningReviewRep;
    private final RestaurantRep restaurantRep;
    private final AdminService adminService;

    // Initialize the repositories and service in the constructor
    public AdminController(DiningReviewRep diningReviewRep, RestaurantRep restaurantRep, AdminService adminService) {
        this.diningReviewRep = diningReviewRep;
        this.restaurantRep = restaurantRep;
        this.adminService = adminService;
    }

    // Endpoint to get reviews by status
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

    // Endpoint to perform review action
    @PutMapping("/reviews/{reviewId}")
    public void performReviewAction(@PathVariable Long reviewId, @RequestBody AdminReview adminReviewAction) {
        // Find the review by id or throw an exception if not found
        DiningReview review = diningReviewRep.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Find the restaurant by id or throw an exception if not found
        Restaurant restaurant = restaurantRep.findById(review.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        // Set the review status based on the admin's decision
        review.setStatus(adminReviewAction.getAdminApproved() ? ReviewStatus.ACCEPTED : ReviewStatus.REJECTED);

        // Save the updated review
        diningReviewRep.save(review);
        // Update the restaurant review scores
        adminService.updateRestaurantReviewScores(restaurant);
    }
}
