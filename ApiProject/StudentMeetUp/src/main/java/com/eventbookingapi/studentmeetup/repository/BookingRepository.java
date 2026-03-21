package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByEventId(String eventId);
    List<Booking> findByEmailId(String emailId);
    List<Booking> findByEventIdAndEmailId(String eventId, String emailId);
}
