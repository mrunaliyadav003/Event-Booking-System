package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Rating;
import com.eventbookingapi.studentmeetup.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("Rating")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;
    @PostMapping("create")
    public Rating create(@RequestBody Rating request) {
        return ratingRepository.save(request);
    }
    @GetMapping("getAll")
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }
    @GetMapping("getByEventId")
    public List<Rating> getByEventId(String eventId) {
        return ratingRepository.findByEventId(eventId);
    }
}