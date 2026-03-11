package com.eventbookingapi.studentmeetup.model;

public class Venue {
    private float id;
    private String name;
    private String address;
    private String town;
    private String postcode_lookup;
    private String postcode;
    private String region;
    private String country;
    private String phone;
    private float latitude;
    private float longitude;
    private String type;
    private float rating;
    private float reviewCount;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTown() {
        return town;
    }

    public String getPostcode_lookup() {
        return postcode_lookup;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getType() {
        return type;
    }

    public float getRating() {
        return rating;
    }

    public float getReviewCount() {
        return reviewCount;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setPostcode_lookup(String postcode_lookup) {
        this.postcode_lookup = postcode_lookup;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setReviewCount(float reviewCount) {
        this.reviewCount = reviewCount;
    }
}
