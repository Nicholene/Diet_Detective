package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor

public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;
    private Double peanutScore;
    private Double eggScore;
    private Double dairyScore;
    private Long restaurantId;
    private String comments;

    private ReviewStatus status;
}
