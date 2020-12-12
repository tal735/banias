package com.app.controller.booking;

import com.app.controller.booking.dto.BookingDto;
import com.app.controller.booking.dto.BookingNoteDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.SessionUser;
import com.app.security.SecurityUtils;
import com.app.service.booking.BookingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @ResponseBody
    public List<BookingDto> getBookings(@RequestParam(required = false, defaultValue = "0") Integer offset) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        List<Booking> bookings = bookingService.getBookings(user.getUserId(), offset);
        List<BookingDto> bookingDtos = bookings.stream().map(BookingDto::new).collect(Collectors.toList());
        return bookingDtos;
    }

    @PostMapping
    @ResponseBody
    public BookingDto book(@RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.book(user.getUserId(), bookingRequest);
        return new BookingDto(booking);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<BookingDto> getBookings(@PathVariable Long id) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.getBookingById(id);
        if (booking == null || !booking.getUser().getId().equals(user.getUserId())) {
            LOGGER.warn("getBookings: User " + user.getUserId() + " is trying to access booking "
                    + booking + ", but doesn't have access.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping("/{id}/notes")
    @ResponseBody
    public ResponseEntity<BookingNoteDto> addNote(@PathVariable(value = "id") Long bookingId, @RequestBody String note) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null || !booking.getUser().getId().equals(user.getUserId())) {
            LOGGER.warn("addNote: User " + user.getUserId() + " is trying to access booking "
                    + booking + ", but doesn't have access.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (StringUtils.isNotBlank(note)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        BookingNote bookingNote = bookingService.addNote(user.getUserId(), bookingId, note);
        BookingNoteDto bookingNoteDto = new BookingNoteDto(bookingNote);
        return ResponseEntity.ok(bookingNoteDto);
    }

    @GetMapping("/{id}/notes")
    @ResponseBody
    public ResponseEntity<List<BookingNoteDto>> getNotes(@PathVariable(value = "id") Long bookingId,
                                                        @RequestParam(required = false, defaultValue = "0") Integer offset) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null || !booking.getUser().getId().equals(user.getUserId())) {
            LOGGER.warn("getNotes: User " + user.getUserId() + " is trying to access booking "
                    + booking + ", but doesn't have access.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<BookingNote> bookingNotes = bookingService.getNotes(bookingId, offset);
        List<BookingNoteDto> bookingNoteDtos = bookingNotes.stream().map(BookingNoteDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(bookingNoteDtos);
    }
}
