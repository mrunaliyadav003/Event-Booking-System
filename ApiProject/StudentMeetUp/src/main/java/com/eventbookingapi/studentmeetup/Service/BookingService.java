package com.eventbookingapi.studentmeetup.Service;

import com.eventbookingapi.studentmeetup.collection.Booking;
import com.eventbookingapi.studentmeetup.collection.Events;
import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.repository.BookingRepository;
import com.eventbookingapi.studentmeetup.repository.EventPublisherRepository;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventPublisherRepository eventRepository;
    private final StudentRepository studentRepository;

    public BookingService(BookingRepository bookingRepository, EventPublisherRepository eventRepository, StudentRepository studentRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.studentRepository = studentRepository;
    }

    public Response createBooking(Booking booking) {
        try {
            System.out.println("Creating booking for event: " + booking.getEventId() + " by user: " + booking.getEmailId());

            if (!isSubscribed(booking.getEmailId())) {
                return Response.builder()
                        .Success(false)
                        .Message("Subscription required to book events")
                        .build();
            }

            // 1. Check if event exists
            Optional<Events> eventOptional = eventRepository.findById(booking.getEventId());
            if (eventOptional.isEmpty()) {
                return Response.builder()
                        .Success(false)
                        .Message("Event not found")
                        .build();
            }

            Events event = eventOptional.get();

            // 2. Check for duplicate booking
            List<Booking> existingBookings = bookingRepository.findByEventIdAndEmailId(
                    booking.getEventId(),
                    booking.getEmailId()
            );

            if (!existingBookings.isEmpty()) {
                return Response.builder()
                        .Success(false)
                        .Message("You have already booked this event")
                        .build();
            }

            // 3. Check if event has reached max participants
            List<Booking> allBookingsForEvent = bookingRepository.findByEventId(booking.getEventId());
            int currentBookings = allBookingsForEvent.stream()
                    .mapToInt(Booking::getNumberOfTickets)
                    .sum();

            int maxParticipants = event.getMaxNumberOfParticipants();
            int requestedTickets = booking.getNumberOfTickets();

            if (currentBookings + requestedTickets > maxParticipants) {
                int availableSlots = maxParticipants - currentBookings;
                return Response.builder()
                        .Success(false)
                        .Message("Booking failed. Only " + availableSlots + " slots available. Event is almost full!")
                        .build();
            }

            // 4. Save booking
            Booking savedBooking = bookingRepository.save(booking);
            System.out.println("Booking created successfully: " + savedBooking.getId());

            // 5. Check if event is now full and notify
            int newTotal = currentBookings + requestedTickets;
            if (newTotal >= maxParticipants) {
                System.out.println("NOTIFICATION: Event " + event.getTitle() + " is now FULL!");
                // TODO: Send notification to publisher
            }

            return Response.builder()
                    .Success(true)
                    .Message("Booking created successfully")
                    .Data(savedBooking)
                    .build();

        } catch (Exception e) {
            System.err.println("Booking creation error: " + e.getMessage());
            e.printStackTrace();
            return Response.builder()
                    .Success(false)
                    .Message("Booking failed: " + e.getMessage())
                    .build();
        }
    }

    public Response cancelBooking(String bookingId, String userEmail) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

            if (bookingOptional.isEmpty()) {
                return Response.builder()
                        .Success(false)
                        .Message("Booking not found")
                        .build();
            }

            Booking booking = bookingOptional.get();

            // Check if user owns this booking
            if (!booking.getEmailId().equals(userEmail)) {
                return Response.builder()
                        .Success(false)
                        .Message("You can only cancel your own bookings")
                        .build();
            }

            bookingRepository.deleteById(bookingId);
            System.out.println("Booking cancelled: " + bookingId);

            return Response.builder()
                    .Success(true)
                    .Message("Booking cancelled successfully")
                    .build();

        } catch (Exception e) {
            System.err.println("Booking cancellation error: " + e.getMessage());
            e.printStackTrace();
            return Response.builder()
                    .Success(false)
                    .Message("Cancellation failed: " + e.getMessage())
                    .build();
        }
    }

    public List<Booking> getMyBookings(String userEmail) {
        return bookingRepository.findByEmailId(userEmail);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByEventId(String eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    private boolean isSubscribed(String emailId) {
        if (emailId == null || emailId.trim().isEmpty()) {
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
