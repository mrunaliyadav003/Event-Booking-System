package com.eventbookingapi.studentmeetup.collection;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    @JsonAlias("name")
    private String Name;
    @JsonAlias("emailId")
    private String EmailId;
    @JsonAlias("subscribed")
    private Boolean Subscribed = false;
}
