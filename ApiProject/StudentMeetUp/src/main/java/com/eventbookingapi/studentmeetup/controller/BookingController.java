package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.BookingCount;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
//import com.eventbookingapi.studentmeetup.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin

@RequestMapping("Booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("create")
    public Booking create(@RequestBody Booking request) {
        return bookingRepository.save(request);
    }
    @GetMapping("getAll")
    public List<Booking> getAll() {
            return bookingRepository.findAll();
    }
    @PutMapping("update")
    public Booking update(@RequestBody Booking request) {
        return bookingRepository.save(request);
    }
    @DeleteMapping("delete")
    public void delete(String id) {
        bookingRepository.deleteById(id);
    }
    @GetMapping("/getAllListCount")
   public List<BookingCount> getAllListCount() {

     List<Booking> oblList=bookingRepository.findAll();

        List<BookingCount> eventCounts = oblList.stream()
                .collect(Collectors.groupingBy(
                        Booking::getEventId, // Group by eventId
                        Collectors.counting() // Count bookings
                ))
                .entrySet().stream()
                .map(entry -> new BookingCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

      return eventCounts;
  }

}
