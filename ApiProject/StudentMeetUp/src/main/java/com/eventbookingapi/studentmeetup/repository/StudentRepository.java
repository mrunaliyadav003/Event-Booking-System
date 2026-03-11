package com.eventbookingapi.studentmeetup.repository;

import com.eventbookingapi.studentmeetup.collection.Events;
import com.eventbookingapi.studentmeetup.collection.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository
        extends MongoRepository<Student, String> {
    @Query("{ 'EmailId': ?0 }")
    List<Student> findByName(String EmailId);
}