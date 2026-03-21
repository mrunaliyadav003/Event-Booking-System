package com.eventbookingapi.studentmeetup.collection;

import lombok.*;

import java.util.Date;
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
    private Double ratingCount;
    private long bookingCount;
}
