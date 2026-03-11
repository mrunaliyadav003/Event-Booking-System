package com.eventbookingapi.studentmeetup.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.UUID;

//@Data
@Builder
// Entity
@Setter
@Getter
@Document(collection = "EventPublisher")
@Data @NoArgsConstructor
@AllArgsConstructor
public class Events {
    @Id
    // @MongoId(FieldType.OBJECT_ID)
    private String  id;
    // @TextIndexed
    private String publisherId;
    //@TextIndexed
    private String title;
    //@TextIndexed
    private String type;
    //@TextIndexed
    private Date date;
    //@TextIndexed
    private String location;
    //@TextIndexed
    private Double cost;
    private Integer maxNumberOfParticipants;


    public Double setCost() {
        return cost;
    }
}

