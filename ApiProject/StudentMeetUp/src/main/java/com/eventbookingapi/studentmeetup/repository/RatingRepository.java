package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByEventId(String id);
}
