package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Events;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface EventPublisherRepository extends MongoRepository<Events, String> {
    @Query("{ $text:{ $search: ?0 }}")
    List<Events> searchByText(String text);
    List<Events> getByDate(Date date);
    List<Events> getByType(String type);
    List<Events> getByLocation(String location);
}
