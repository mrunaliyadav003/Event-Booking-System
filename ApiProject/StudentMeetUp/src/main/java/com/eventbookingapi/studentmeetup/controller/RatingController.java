package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Rating;
import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("Rating")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @PostMapping("create")
    public ResponseEntity<Response> create(@RequestBody Rating request) {
        try {
            // Validate required fields
            if (request.getEventId() == null || request.getEventId().isEmpty()) {
                return ResponseEntity.badRequest().body(Response.builder()
                        .Success(false)
                        .Message("Event ID is required")
                        .build());
            }

            if (request.getStudentId() == null || request.getStudentId().isEmpty()) {
                return ResponseEntity.badRequest().body(Response.builder()
                        .Success(false)
                        .Message("Student ID is required")
                        .build());
            }

            if (request.getScore() < 1 || request.getScore() > 5) {
                return ResponseEntity.badRequest().body(Response.builder()
                        .Success(false)
                        .Message("Rating score must be between 1 and 5")
                        .build());
            }

            Rating savedRating = ratingRepository.save(request);

            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Message("Rating saved successfully")
                    .Data(savedRating)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error saving rating: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<Response> getAll() {
        try {
            List<Rating> ratings = ratingRepository.findAll();
            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Data(ratings)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error retrieving ratings: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("getByEventId")
    public ResponseEntity<Response> getByEventId(@RequestParam String eventId) {
        try {
            List<Rating> ratings = ratingRepository.findByEventId(eventId);
            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Data(ratings)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error retrieving ratings: " + e.getMessage())
                            .build());
        }
    }
}