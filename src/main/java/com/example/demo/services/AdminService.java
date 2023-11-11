package com.example.demo.services;

import com.example.demo.model.DiningReview;
import com.example.demo.model.Restaurant;
import com.example.demo.model.ReviewStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
public class AdminService {
    public void updateRestaurantReviewScores(Restaurant restaurant) {
        List<DiningReview> reviews = reviewRepository.findReviewsByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);
        if (reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int peanutSum = 0;
        int peanutCount = 0;
        int dairySum = 0;
        int dairyCount = 0;
        int eggSum = 0;
        int eggCount = 0;
        for (DiningReview r : reviews) {
            if (!ObjectUtils.isEmpty(r.getPeanutScore())) {
                peanutSum += r.getPeanutScore();
                peanutCount++;
            }
            if (!ObjectUtils.isEmpty(r.getDairyScore())) {
                dairySum += r.getDairyScore();
                dairyCount++;
            }
            if (!ObjectUtils.isEmpty(r.getEggScore())) {
                eggSum += r.getEggScore();
                eggCount++;
            }
        }


        int totalCount = peanutCount + dairyCount + eggCount ;
        int totalSum = peanutSum + dairySum + eggSum;


        float overallScore = (float) totalSum / totalCount;
        restaurant.setRating(decimalFormat.format(overallScore));


        if (peanutCount > 0) {
            float PeanutRating = (float) peanutSum / peanutCount;
            restaurant.setPeanutRating(decimalFormat.format(PeanutRating));
        }


        if (dairyCount > 0) {
            float DairyRating = (float) dairySum / dairyCount;
            restaurant.setDairyRating(decimalFormat.format(DairyRating));
        }


        if (eggCount > 0) {
            float EggRating = (float) eggSum / eggCount;
            restaurant.setEggRating(decimalFormat.format(EggRating));
        }


        restaurantRepository.save(restaurant);
    }
}


