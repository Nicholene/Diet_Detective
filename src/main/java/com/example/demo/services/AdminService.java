package com.example.demo.services;

import com.example.demo.model.DiningReview;
import com.example.demo.model.Restaurant;
import com.example.demo.model.ReviewStatus;
import com.example.demo.repository.DiningReviewRep;
import com.example.demo.repository.RestaurantRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class AdminService {

    // Repositories for DiningReview and Restaurant
    @Autowired
    DiningReviewRep reviewRepository;
    @Autowired
    RestaurantRep restaurantRepository;

    // DecimalFormat for formatting the scores
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // Method to update restaurant review scores
    public void updateRestaurantReviewScores(Restaurant restaurant) {
        // Fetch the reviews for the restaurant
        List<DiningReview> reviews = reviewRepository.findDiningReviewsByStatusAndRestaurantId((ReviewStatus.ACCEPTED),restaurant.getId());

        // If no reviews, throw an exception
        if (reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Variables to hold the sum and count of scores for each category
        int peanutSum = 0, peanutCount = 0, dairySum = 0, dairyCount = 0, eggSum = 0, eggCount = 0;

        // Iterate over the reviews
        for (DiningReview r : reviews) {
            // If the review has a peanut score, add it to the sum and increment the count
            if (!ObjectUtils.isEmpty(r.getPeanutScore())) {
                peanutSum += r.getPeanutScore();
                peanutCount++;
            }
            // Do the same for dairy and egg scores
            if (!ObjectUtils.isEmpty(r.getDairyScore())) {
                dairySum += r.getDairyScore();
                dairyCount++;
            }
            if (!ObjectUtils.isEmpty(r.getEggScore())) {
                eggSum += r.getEggScore();
                eggCount++;
            }
        }

        // Calculate the total count and sum
        int totalCount = peanutCount + dairyCount + eggCount;
        int totalSum = peanutSum + dairySum + eggSum;

        // Calculate the overall score and set it in the restaurant
        float overallScore = (float) totalSum / totalCount;
        restaurant.setRating(decimalFormat.format(overallScore));

        // If there are peanut scores, calculate the average and set it in the restaurant
        if (peanutCount > 0) {
            float PeanutRating = (float) peanutSum / peanutCount;
            restaurant.setPeanutRating(decimalFormat.format(PeanutRating));
        }

        // Do the same for dairy and egg scores
        if (dairyCount > 0) {
            float DairyRating = (float) dairySum / dairyCount;
            restaurant.setDairyRating(decimalFormat.format(DairyRating));
        }
        if (eggCount > 0) {
            float EggRating = (float) eggSum / eggCount;
            restaurant.setEggRating(decimalFormat.format(EggRating));
        }

        // Save the updated restaurant in the repository
        restaurantRepository.save(restaurant);
    }
}
