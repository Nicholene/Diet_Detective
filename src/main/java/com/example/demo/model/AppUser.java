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
public class AppUser {
    @Id
    @GeneratedValue
    private Long Id;
    @Column(unique = true)
    private String userName;

    private String city;
    private String state;
    private String zipCode;

    private Boolean peanutAllergies;
    private Boolean eggAllergies;
    private Boolean dairyAllergies;
}