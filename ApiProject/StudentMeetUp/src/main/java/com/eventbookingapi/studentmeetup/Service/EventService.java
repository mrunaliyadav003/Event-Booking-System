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
        List<Rating> ratingList = ratingRepository.findAll();
        List<Booking> bookingList = bookingRepository.findAll();

        return eventPublisherRepository.findAll().stream().map(e -> {
            EventDTO dto = new EventDTO();
            dto.setId(e.getId());
            dto.setPublisherId(e.getPublisherId());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setDate(e.getDate());
            dto.setLocation(e.getLocation());
            dto.setCost(e.getCost());
            dto.setMaxNumberOfParticipants(e.getMaxNumberOfParticipants());

            long bookingCount = bookingList.stream()
                    .filter(b -> b.getEventId().equalsIgnoreCase(e.getId()))
                    .mapToLong(Booking::getNumberOfTickets)
                    .sum();

            double ratingAverage = ratingList.stream()
                    .filter(r -> r.getEventId().equalsIgnoreCase(e.getId()))
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);

            dto.setBookingCount(bookingCount);
            dto.setRatingCount(ratingAverage);
            return dto;
        }).collect(Collectors.toList());
    }


}
