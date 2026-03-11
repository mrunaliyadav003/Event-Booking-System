package com.eventbookingapi.studentmeetup.collection;

import lombok.*;

import java.util.Date;
import java.util.OptionalDouble;


@Builder
// Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventDTO {
    private String  id;
    private String publisherId;
    private String title;
    private String type;
    private Date date;
    private String location;
    private Double cost;
    private Integer maxNumberOfParticipants;
    private  OptionalDouble  ratingCount;
    private  long bookingCount;

    public void EventDTO(String publisherId,String id,String title,String type,Date date,String location,Double cost,Integer maxNumberOfParticipants,long bookingCount) {
        this.setPublisherId(publisherId);
        this.setId(id);
        this.setTitle(title);
        this.setType(type);
        this.setDate(date);
        this.setLocation(location);
        this.setCost(cost);
        this.setMaxNumberOfParticipants(maxNumberOfParticipants);
        this.setRatingCount(ratingCount);
        this.setBookingCount(bookingCount);
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
    private void setrId(String id) {
        this.id = id;
    }

    public void getId(String id) {
        this.setId(id);
    }

    public void getPublisherId(String publisherId) {
        this.setPublisherId(publisherId);
    }

    public void getTitle(String title) {
        this.setTitle(title);
    }

    public void getDate(Date date) {
        this.setDate(date);
    }

    public void getLocation(String location) {
        this.setLocation(location);
    }

    public void getCost(Double cost) {
        this.setCost(cost);
    }

    public void getMaxNumberOfParticipants(Integer maxNumberOfParticipants) {
        this.setMaxNumberOfParticipants(maxNumberOfParticipants);
    }

    public void getBookingCount(long bookingCount) {
        this.setBookingCount(bookingCount);
    }

    public void getType(String type) {
        this.setType(type);
    }

    public void getRatingCount(OptionalDouble  ratingCount) {
        this.setRatingCount(ratingCount);
    }
}
