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

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;

    private String lineOne;
    private String city;
    private String state;
    private String zipCode;

    private String phoneNumber;
    private String website;


    private Double rating;
    private Double peanutRating;
    private Double eggRating;
    private Double dairyRating;
}



