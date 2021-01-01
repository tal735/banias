package com.app.controller.booking;

import com.app.controller.booking.dto.BookingNoteDto;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.SessionUser;
import com.app.service.security.SecurityUtils;
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
@RequestMapping("/api/booking/notes")
public class BookingNotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingNotesController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingNotesController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<BookingNoteDto>> getNotes(@RequestParam(required = false, defaultValue = "0") Integer offset) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Long bookingId = null;
        Booking booking = bookingService.getBookingById(bookingId);
        List<BookingNote> bookingNotes = bookingService.getNotes(bookingId, offset);
        List<BookingNoteDto> bookingNoteDtos = bookingNotes.stream().map(BookingNoteDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(bookingNoteDtos);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<BookingNoteDto> addNote(@RequestBody String note) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Long bookingId = null;
        Booking booking = bookingService.getBookingById(bookingId);
        if (StringUtils.isBlank(note)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        BookingNote bookingNote = bookingService.addNote(user.getUserId(), bookingId, note);
        BookingNoteDto bookingNoteDto = new BookingNoteDto(bookingNote);
        return ResponseEntity.ok(bookingNoteDto);
    }
}
