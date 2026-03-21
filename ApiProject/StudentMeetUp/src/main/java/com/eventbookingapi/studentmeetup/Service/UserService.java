package com.eventbookingapi.studentmeetup.Service;

import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.collection.User;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import com.eventbookingapi.studentmeetup.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public UserService(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
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

            // Set creation timestamp and defaults
            user.setCreatedAt(LocalDateTime.now());
            user.setActive(true);

            // Set default role if not specified
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("STUDENT");
            }

            ensureStudentSubscription(user.getName(), user.getEmail());

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

    private void ensureStudentSubscription(String name, String email) {
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        Student student = studentRepository.findByEmailId(email)
                .stream()
                .findFirst()
                .orElseGet(Student::new);

        student.setEmailId(email);
        if (name != null && !name.trim().isEmpty()) {
            student.setName(name);
        }
        student.setSubscribed(true);

        studentRepository.save(student);
    }
}
