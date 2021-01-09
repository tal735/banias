package com.app.controller.booking;

import com.app.controller.booking.dto.BookingNoteDto;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.service.booking.BookingService;
import com.app.service.security.SecurityUtils;
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

    @GetMapping("/{reference}")
    @ResponseBody
    public ResponseEntity getNotes(@PathVariable String reference, @RequestParam(required = false) Long offset) {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        Booking booking = bookingService.getByReference(reference);
        if (booking == null || !booking.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Booking does not belong to user.");
        }
        List<BookingNote> bookingNotes = bookingService.getNotes(booking.getId(), offset);
        List<BookingNoteDto> bookingNoteDtos = bookingNotes.stream().map(BookingNoteDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(bookingNoteDtos);
    }

    @PostMapping("/{reference}")
    @ResponseBody
    public ResponseEntity addNote(@PathVariable String reference, @RequestBody String note) {
        if (StringUtils.isBlank(note)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        Booking booking = bookingService.getByReference(reference);
        if (booking == null || !booking.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Booking does not belong to user.");
        }
        BookingNote bookingNote = bookingService.addNote(userId, booking.getId(), note);
        BookingNoteDto bookingNoteDto = new BookingNoteDto(bookingNote);
        return ResponseEntity.ok(bookingNoteDto);
    }
}
