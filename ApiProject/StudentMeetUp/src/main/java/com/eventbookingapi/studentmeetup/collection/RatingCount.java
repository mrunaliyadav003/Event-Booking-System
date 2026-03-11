package com.eventbookingapi.studentmeetup.collection;

import lombok.*;

@Builder

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RatingCount {
    private String eventId;
    private long count;
}
