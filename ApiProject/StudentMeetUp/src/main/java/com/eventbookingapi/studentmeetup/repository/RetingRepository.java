package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.Rating;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface RetingRepository extends  MongoRepository<Rating, String>
{
}
