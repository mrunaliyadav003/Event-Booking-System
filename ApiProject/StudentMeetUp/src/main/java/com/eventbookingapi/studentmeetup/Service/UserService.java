package com.eventbookingapi.studentmeetup.Service;

import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.collection.User;
import com.eventbookingapi.studentmeetup.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response registerUser(User user) {
        try {
            System.out.println("Registering user: " + user.getEmail());

            // Check if email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                System.out.println("Email already exists: " + user.getEmail());
                return Response.builder()
                        .Success(false)
                        .Message("Email already registered")
                        .build();
            }

            // Set creation timestamp
            user.setCreatedAt(LocalDateTime.now());
            user.setActive(true);

            // Save user to database
            User savedUser = userRepository.save(user);
            System.out.println("User registered successfully: " + savedUser.getId());

            return Response.builder()
                    .Success(true)
                    .Message("User registered successfully")
                    .Data(savedUser.getId())
                    .build();
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return Response.builder()
                    .Success(false)
                    .Message("Registration failed: " + e.getMessage())
                    .build();
        }
    }

    public Response loginUser(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return Response.builder()
                        .Success(false)
                        .Message("Invalid email or password")
                        .build();
            }

            User user = userOptional.get();

            // Check if user is active
            if (!user.isActive()) {
                return Response.builder()
                        .Success(false)
                        .Message("Account is deactivated")
                        .build();
            }

            // Verify password
            if (!user.getPassword().equals(password)) {
                return Response.builder()
                        .Success(false)
                        .Message("Invalid email or password")
                        .build();
            }

            // Update last login time
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);

            return Response.builder()
                    .Success(true)
                    .Message("Login successful")
                    .Data(user)
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .Success(false)
                    .Message("Login failed: " + e.getMessage())
                    .build();
        }
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
