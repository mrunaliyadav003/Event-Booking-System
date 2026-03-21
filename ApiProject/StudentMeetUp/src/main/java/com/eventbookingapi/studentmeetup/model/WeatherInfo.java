package com.eventbookingapi.studentmeetup.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    private String location;
    private String country;
    private String date;
    private double latitude;
    private double longitude;
    private Double temperatureMax;
    private Double temperatureMin;
    private Double precipitationProbability;
}
