package com.example.demo.repository;

import com.example.demo.model.DiningReview;
import com.example.demo.model.ReviewStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface DiningReviewRep extends JpaRepository<DiningReview, Long> {
    List<DiningReview> findDiningReviewByRestaurantId(Long restaurantId);
    List<DiningReview> findDiningReviewsByStatusAndRestaurantId(ReviewStatus status, Long restaurantId);
    List<DiningReview> findDiningReviewsByStatus(ReviewStatus status);
}
