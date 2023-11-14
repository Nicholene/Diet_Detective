package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.persistence.Id;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiningReview {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long restaurantId;
    private String comments;

    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;

    private ReviewStatus status;

    }

