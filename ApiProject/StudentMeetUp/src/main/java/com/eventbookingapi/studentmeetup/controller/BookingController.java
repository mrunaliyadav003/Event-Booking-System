package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.BookingCount;
import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import com.eventbookingapi.studentmeetup.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("Booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @PostMapping("create")
    public ResponseEntity<Response> create(@RequestBody Booking request) {
        Response response = bookingService.createBooking(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<Response> getAll() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Data(bookings)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error retrieving bookings: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("getMyBookings")
    public ResponseEntity<Response> getMyBookings(@RequestParam String email) {
        try {
            List<Booking> bookings = bookingService.getMyBookings(email);
            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Data(bookings)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error retrieving your bookings: " + e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("cancel/{bookingId}")
    public ResponseEntity<Response> cancelBooking(@PathVariable String bookingId, @RequestParam String email) {
        Response response = bookingService.cancelBooking(bookingId, email);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("getByEventId")
    public ResponseEntity<Response> getByEventId(@RequestParam String eventId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByEventId(eventId);
            return ResponseEntity.ok(Response.builder()
                    .Success(true)
                    .Data(bookings)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .Success(false)
                            .Message("Error retrieving bookings: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("update")
    public Booking update(@RequestBody Booking request) {
        return bookingRepository.save(request);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam String id) {
        bookingRepository.deleteById(id);
    }
    @GetMapping("/getAllListCount")
   public List<BookingCount> getAllListCount() {

     List<Booking> oblList=bookingRepository.findAll();

        List<BookingCount> eventCounts = oblList.stream()
                .filter(booking -> booking.getEventId() != null && !booking.getEventId().trim().isEmpty())
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
