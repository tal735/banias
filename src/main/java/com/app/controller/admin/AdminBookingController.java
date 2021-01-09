package com.app.controller.admin;

import com.app.controller.admin.dto.BookingDto;
import com.app.controller.admin.request.BookingFindRequest;
import com.app.controller.admin.request.BookingUpdateRequest;
import com.app.controller.booking.dto.BookingNoteDto;
import com.app.controller.validator.BookingValidator;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.service.booking.BookingService;
import com.app.service.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public AdminBookingController(BookingService bookingService, BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
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

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateBooking(@PathVariable Long id, @RequestBody BookingUpdateRequest updateRequest) {
        Map<String, String> errors = bookingValidator.validateForAdminUpdate(id, updateRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Booking booking = bookingService.getById(id);
        booking.setDateFrom(updateRequest.getDateFrom());
        booking.setDateTo(updateRequest.getDateTo());
        booking.setGuests(updateRequest.getGuests());
        booking.setContactName(updateRequest.getContactName());
        booking.setPhone(updateRequest.getPhone());
        booking.setStatus(updateRequest.getStatus());
        booking.setDateModified(new Date());
        bookingService.saveOrUpdate(booking);
        return ResponseEntity.ok(new BookingDto(booking, true));
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
