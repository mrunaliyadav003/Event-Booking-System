package com.eventbookingapi.studentmeetup.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@Builder
// Entity
@Setter
@Getter
@Document(collection = "Booking")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Booking {
    @Id
    private String id;
    private String eventId;
    private String emailId;
    private String publisherId;
    private Date bookingDate;
    private int numberOfTickets;
    private int sequenceNo;
    private String title;
    private String type;
    private Date date;
    private String location;
    private Double cost;

}
