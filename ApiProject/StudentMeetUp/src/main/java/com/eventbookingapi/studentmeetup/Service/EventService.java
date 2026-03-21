package com.eventbookingapi.studentmeetup.Service;

import com.eventbookingapi.studentmeetup.collection.*;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import com.eventbookingapi.studentmeetup.repository.EventPublisherRepository;
import com.eventbookingapi.studentmeetup.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventPublisherRepository eventPublisherRepository;

    public  List<EventDTO>  getallEventsRating()
    {
        List<Rating> oblRatingList=ratingRepository.findAll();
        List<Booking> obkBookingList=bookingRepository.findAll();


        List<RatingCount> ratingCountMap = oblRatingList.stream()
                .collect(Collectors.groupingBy(
                        Rating::getEventId, // Group by eventId
                        Collectors.counting() // Count bookings
                ))
                .entrySet().stream()
                .map(entry -> new RatingCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        List<BookingCount> bookingCountMap = obkBookingList.stream()
                .collect(Collectors.groupingBy(
                        Booking::getEventId, // Group by eventId
                        Collectors.counting() // Count bookings
                ))
                .entrySet().stream()
                .map(entry -> new BookingCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());



        List<EventDTO>  result =  eventPublisherRepository.findAll().stream().map(e -> {
            EventDTO dto = new EventDTO();
            dto.getId(e.getId());
            dto.getPublisherId(e.getPublisherId());
            dto.getTitle(e.getTitle());
            dto.getTitle(e.getType());
            dto.getDate(e.getDate());
            dto.getLocation(e.getLocation());
            dto.getCost(e.getCost());
            dto.getCost(e.setCost());
            dto.getMaxNumberOfParticipants(e.getMaxNumberOfParticipants());
            long bookingCount =bookingCountMap.stream()
                    .filter(o -> o.getEventId().equalsIgnoreCase(e.getId()))
                    .count();
            long ratingCount =ratingCountMap.stream()
                    .filter(o -> o.getEventId().equalsIgnoreCase(e.getId()))
                    .count();

            dto.getBookingCount(bookingCount);
            dto.getBookingCount(ratingCount);
            return  dto;
        }).collect(Collectors.toList());




        return result;
    }


}
