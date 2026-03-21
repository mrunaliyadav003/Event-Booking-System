package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.EventDTO;
import com.eventbookingapi.studentmeetup.collection.Events;
import com.eventbookingapi.studentmeetup.collection.Rating;
import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import com.eventbookingapi.studentmeetup.repository.EventPublisherRepository;
import com.eventbookingapi.studentmeetup.repository.RatingRepository;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path="events")
public class EventPublisherController {
    @Autowired
    private  EventPublisherRepository eventPublisherRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookingRepository  bookingRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Events request) {
        String validationError = validateEvent(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(Map.of("message", validationError));
        }
        if (!isSubscribed(request.getPublisherId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Subscription required to publish events"));
        }

        try {
            return ResponseEntity.ok(eventPublisherRepository.save(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error saving event: " + e.getMessage()));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam String id, @RequestBody Events request) {
        request.setId(id);
        String validationError = validateEvent(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(Map.of("message", validationError));
        }
        if (!isSubscribed(request.getPublisherId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Subscription required to publish events"));
        }

        try {
            return ResponseEntity.ok(eventPublisherRepository.save(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating event: " + e.getMessage()));
        }
    }

    @GetMapping("getAll")
  public  List<EventDTO> getAll() {
        List<Events> events = eventPublisherRepository.findAll();

      return GetEventDtoData(events) ;
  }

    @GetMapping("getAllEventData")
    public  List<EventDTO> getAllEventData() {

        List<Events>  eventresult =  eventPublisherRepository.findAll();
        List<Rating>  RatingList= ratingRepository.findAll();
        List<Booking> BookingList=bookingRepository.findAll();

        List<EventDTO>  result = new ArrayList<>();
         result =  eventresult.stream().map(e -> {
            EventDTO dto = new EventDTO();
            dto.setId(e.getId());
            dto.setPublisherId(e.getPublisherId());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setDate(e.getDate());
            dto.setLocation(e.getLocation());
            dto.setCost(e.getCost());
            dto.setMaxNumberOfParticipants(e.getMaxNumberOfParticipants());

           String eventId=e.getId();


            long bookingCount = (long) BookingList.stream()
                    .filter(o -> Objects.equals(o.getEventId(), eventId))
                    .mapToDouble(Booking::getNumberOfTickets)
                     .sum();

           //  OptionalDouble ratingSum = RatingList.stream()
             //        .filter(o -> Objects.equals(o.getEventId(), eventId))
             //        .mapToDouble(Rating::getScore) // convert to DoubleStream
             //        .reduce(Double::sum); // returns OptionalDouble

             double averageScore = RatingList.stream()
                     .filter(r -> Objects.equals(r.getEventId(), eventId)) // filter by eventId
                     .mapToDouble(Rating::getScore) // extract score
                     .average()
                     .orElse(0.0); // compute average

            dto.setBookingCount(bookingCount);
            dto.setRatingCount(averageScore);
            return  dto;
        }).collect(Collectors.toList());

        return result;
    }

    @GetMapping("getById")
    public Events getById(@RequestParam String id) {
        Events item = new Events();
        item= eventPublisherRepository.findById(id).get();
        return item;
    }

    @DeleteMapping("delete")
    public String delete(@RequestParam String id) {
        eventPublisherRepository.deleteById(id);
    return "Deleted Successfully";
    }
    @GetMapping("SeachByText")
    public List<EventDTO> searchByText(@RequestParam String text) {
       List<Events> events = eventPublisherRepository.searchByText(text);

       return GetEventDtoData(events);
     //   return null;
    }
    @GetMapping("getByDate")
    public List<EventDTO> getByDate(@RequestParam("date")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date requestdate)
    {
        //return eventPublisherService.getByDate((date);
        List<Events> events =eventPublisherRepository.getByDate(requestdate);
        return GetEventDtoData(events);
    }

    @GetMapping("getType")
    public List<EventDTO> getByType(@RequestParam String type) {
        List<Events> events =eventPublisherRepository.getByType(type);
        return GetEventDtoData(events);
    }

    @GetMapping("getLocation")
    public List<EventDTO> getByLocation(@RequestParam String location) {
        List<Events> events =eventPublisherRepository.getByLocation(location);
        return GetEventDtoData(events);
    }
    public  List<EventDTO> GetEventDtoData(List<Events> eventresult) {

     //   List<Events>  eventresult =  eventPublisherRepository.findAll();
        List<EventDTO>  result = new ArrayList<>();
        if(eventresult!=null) {
            List<Rating> RatingList = ratingRepository.findAll();
            List<Booking> BookingList = bookingRepository.findAll();

            result = eventresult.stream().map(e -> {
                EventDTO dto = new EventDTO();
                dto.setId(e.getId());
                dto.setPublisherId(e.getPublisherId());
                dto.setTitle(e.getTitle());
                dto.setType(e.getType());
                dto.setDate(e.getDate());
                dto.setLocation(e.getLocation());
                dto.setCost(e.getCost());
                dto.setMaxNumberOfParticipants(e.getMaxNumberOfParticipants());

                String eventId = e.getId();


                long bookingCount = (long) BookingList.stream()
                        .filter(o -> Objects.equals(o.getEventId(), eventId))
                        .mapToDouble(Booking::getNumberOfTickets)
                        .sum();

                //  OptionalDouble ratingSum = RatingList.stream()
                //        .filter(o -> Objects.equals(o.getEventId(), eventId))
                //        .mapToDouble(Rating::getScore) // convert to DoubleStream
                //        .reduce(Double::sum); // returns OptionalDouble

                double averageScore = RatingList.stream()
                        .filter(r -> Objects.equals(r.getEventId(), eventId)) // filter by eventId
                        .mapToDouble(Rating::getScore) // extract score
                        .average()
                        .orElse(0.0); // compute average

                dto.setBookingCount(bookingCount);
                dto.setRatingCount(averageScore);
                return dto;
            }).collect(Collectors.toList());
        }
        return result;
    }

    private String validateEvent(Events request) {
        if (request == null) {
            return "Event payload is required";
        }
        if (isBlank(request.getPublisherId())) {
            return "Publisher ID is required";
        }
        if (isBlank(request.getTitle())) {
            return "Title is required";
        }
        if (isBlank(request.getType())) {
            return "Type is required";
        }
        if (request.getDate() == null) {
            return "Event date is required";
        }
        if (isBlank(request.getLocation())) {
            return "Location is required";
        }
        if (request.getCost() == null || request.getCost() < 0) {
            return "Cost must be 0 or more";
        }
        if (request.getMaxNumberOfParticipants() == null || request.getMaxNumberOfParticipants() < 1) {
            return "Max number of participants must be at least 1";
        }

        String normalizedType = request.getType().trim().toLowerCase(Locale.ROOT);
        if (!normalizedType.equals("sport") && !normalizedType.equals("concert") && !normalizedType.equals("other")) {
            return "Type must be sport, concert, or other";
        }

        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isSubscribed(String emailId) {
        if (isBlank(emailId)) {
            return false;
        }
        return studentRepository.findByEmailId(emailId)
                .stream()
                .anyMatch(this::isStudentSubscribed);
    }

    private boolean isStudentSubscribed(Student student) {
        return student != null && Boolean.TRUE.equals(student.getSubscribed());
    }
}
