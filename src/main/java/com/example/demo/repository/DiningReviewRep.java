package com.example.demo.repository;

import com.example.demo.model.DiningReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface DiningReviewRep extends JpaRepository<DiningReview, Long> {

}
