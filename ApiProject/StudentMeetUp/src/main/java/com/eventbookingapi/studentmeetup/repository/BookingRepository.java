package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends MongoRepository<Booking,String> {

}
