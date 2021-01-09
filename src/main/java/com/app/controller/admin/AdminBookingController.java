package com.app.controller.admin;

import com.app.controller.admin.dto.BookingDto;
import com.app.controller.booking.dto.BookingNoteDto;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.service.booking.BookingService;
import com.app.service.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/find")
    @ResponseBody
    public List<BookingDto> findBookings(@RequestBody BookingFindRequest bookingFindRequest) {
        List<Booking> bookings = bookingService.getForDates(
                bookingFindRequest.getDateFromMin(),
                bookingFindRequest.getDateFromMax(),
                bookingFindRequest.getDateToMin(),
                bookingFindRequest.getDateToMax());
        return bookings.stream().map(b -> new BookingDto(b, false)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookingDto getBooking(@PathVariable Long id) {
        Booking booking = bookingService.getById(id);
        return new BookingDto(booking, true);
    }

    @GetMapping("/{id}/notes")
    @ResponseBody
    public List<BookingNoteDto> getNotes(@PathVariable Long id, @RequestParam(required = false) Long offset) {
        List<BookingNote> bookingNotes = bookingService.getNotes(id, offset);
        return bookingNotes.stream().map(BookingNoteDto::new).collect(Collectors.toList());
    }

    @PostMapping("/{id}/notes")
    @ResponseBody
    public void postNode(@PathVariable Long id, @RequestBody String note) {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        bookingService.addNote(userId, id, note);
    }
}
