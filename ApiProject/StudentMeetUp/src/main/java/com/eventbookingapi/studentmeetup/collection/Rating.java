package com.eventbookingapi.studentmeetup.collection;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@Builder
// Entity
@Setter
@Getter
@Document(collection = "Rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    private String id;
    private String eventId;
    private String studentId;
    private int score; // e.g., 1-5
    private String comment;
    private Date ratingDate;
}