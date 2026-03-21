package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.Service.BookingService;
import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.BookingCount;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void getAllListCount_skipsNullOrBlankEventIds() {
        Booking valid1 = new Booking();
        valid1.setEventId("event-1");

        Booking nullEvent = new Booking();
        nullEvent.setEventId(null);

        Booking blankEvent = new Booking();
        blankEvent.setEventId(" ");

        Booking valid2 = new Booking();
        valid2.setEventId("event-1");

        when(bookingRepository.findAll()).thenReturn(List.of(valid1, nullEvent, blankEvent, valid2));

        List<BookingCount> counts = bookingController.getAllListCount();

        assertEquals(1, counts.size());
        BookingCount count = counts.get(0);
        assertEquals("event-1", count.getEventId());
        assertEquals(2L, count.getCount());
    }
}
