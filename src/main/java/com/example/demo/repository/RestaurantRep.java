package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface RestaurantRep extends JpaRepository<Restaurant, Long> {
}
