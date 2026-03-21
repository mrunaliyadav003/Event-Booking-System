package com.eventbookingapi.studentmeetup.collection;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role; // Optional: STUDENT or ADMIN (defaults to STUDENT if not provided)
}
