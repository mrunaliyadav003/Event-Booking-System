package com.eventbookingapi.studentmeetup.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
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
@Data @NoArgsConstructor
@AllArgsConstructor

@Document(collection = "Student")
public class Student {
    @Id
    //@MongoId(FieldType.OBJECT_ID)
    private String  id;
    private String Name;
    private String EmailId;
    private boolean Subscribed=false;
}
