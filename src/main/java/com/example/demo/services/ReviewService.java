package com.example.demo.services;

import com.example.demo.model.DiningReview;
import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ReviewService {

    // Repository for User
    UserRep userRepository;

    // Method to validate a user review
    public void validateUserReview(DiningReview review) {
        // Check if the review name is empty
        if (ObjectUtils.isEmpty(review.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Check if the restaurant ID in the review is empty
        if (ObjectUtils.isEmpty(review.getRestaurantId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Check if all the scores in the review are empty
        if (ObjectUtils.isEmpty(review.getPeanutScore()) &&
                ObjectUtils.isEmpty(review.getDairyScore()) &&
                ObjectUtils.isEmpty(review.getEggScore())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Check if a user with the review name exists
        Optional<AppUser> optionalUser = userRepository.findUserByUserName(review.getName());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
