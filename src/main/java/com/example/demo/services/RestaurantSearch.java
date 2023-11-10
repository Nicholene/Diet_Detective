package com.example.demo.services;

import com.example.demo.repository.DiningReviewRep;
import com.example.demo.repository.RestaurantRep;

public class RestaurantSearch {

    private RestaurantRep restaurantRep;

    private DiningReviewRep diningReviewRep;

    public RestaurantSearch(RestaurantRep restaurantRep) {this.restaurantRep = restaurantRep;}

    public String RestaurantData () {
        long size = (long)restaurantRep.count();
        long counter = size;
            while (counter>0) {
                restaurantRep.getReferenceById(counter).getName();
                counter--;
        }

    }

}
