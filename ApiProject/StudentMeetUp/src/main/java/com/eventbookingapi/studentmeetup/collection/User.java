package com.eventbookingapi.studentmeetup.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String phone;

    @Builder.Default
    private String role = "STUDENT"; // STUDENT or ADMIN

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    @Builder.Default
    private boolean active = true;
}
