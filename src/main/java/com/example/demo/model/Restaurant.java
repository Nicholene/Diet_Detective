package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    private String rating;
    private String peanutRating;
    private String eggRating;
    private String dairyRating;
}



