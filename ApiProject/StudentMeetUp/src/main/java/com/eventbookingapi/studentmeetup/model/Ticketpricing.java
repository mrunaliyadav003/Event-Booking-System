package com.eventbookingapi.studentmeetup.model;

public class Ticketpricing {
    private float minPrice;
    private float maxPrice;


    // Getter Methods

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    // Setter Methods

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
