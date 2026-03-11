package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.EventDTO;
import com.eventbookingapi.studentmeetup.collection.Events;
import com.eventbookingapi.studentmeetup.collection.Rating;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import com.eventbookingapi.studentmeetup.repository.EventPublisherRepository;
import com.eventbookingapi.studentmeetup.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PostMapping("create")
    public Events create(@RequestBody Events request) {
                return eventPublisherRepository.save(request);
    }

    @PutMapping("update")
    public Events Update(String id,@RequestBody Events request) {
        request.setId(id);
        return eventPublisherRepository.save(request);
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
                    .mapToDouble(Booking::getNumberofTickets)
                     .sum();

           //  OptionalDouble ratingSum = RatingList.stream()
             //        .filter(o -> Objects.equals(o.getEventId(), eventId))
             //        .mapToDouble(Rating::getScore) // convert to DoubleStream
             //        .reduce(Double::sum); // returns OptionalDouble

             OptionalDouble averageScore = RatingList.stream()
                     .filter(r -> Objects.equals(r.getEventId(), eventId)) // filter by eventId
                     .mapToDouble(Rating::getScore) // extract score
                     .average(); // compute average

            dto.setBookingCount(bookingCount);
            dto.setRatingCount(averageScore);
            return  dto;
        }).collect(Collectors.toList());

        return result;
    }

    @GetMapping("getById")
    public Events getById( String id) {
        Events item = new Events();
        item= eventPublisherRepository.findById(id).get();
        return item;
    }

    @DeleteMapping("delete")
    public String delete( String id) {
        eventPublisherRepository.deleteById(id);
    return "Deleted Successfully";
    }
    @GetMapping("SeachByText")
    public List<EventDTO> searchByText(String text) {
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
    public List<EventDTO> getByType(String type) {
        List<Events> events =eventPublisherRepository.getByType(type);
        return GetEventDtoData(events);
    }

    @GetMapping("getLocation")
    public List<EventDTO> getByLocation(String location) {
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
                        .mapToDouble(Booking::getNumberofTickets)
                        .sum();

                //  OptionalDouble ratingSum = RatingList.stream()
                //        .filter(o -> Objects.equals(o.getEventId(), eventId))
                //        .mapToDouble(Rating::getScore) // convert to DoubleStream
                //        .reduce(Double::sum); // returns OptionalDouble

                OptionalDouble averageScore = RatingList.stream()
                        .filter(r -> Objects.equals(r.getEventId(), eventId)) // filter by eventId
                        .mapToDouble(Rating::getScore) // extract score
                        .average(); // compute average

                dto.setBookingCount(bookingCount);
                dto.setRatingCount(averageScore);
                return dto;
            }).collect(Collectors.toList());
        }
        return result;
    }

}
