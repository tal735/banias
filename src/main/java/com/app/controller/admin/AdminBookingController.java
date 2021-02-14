package com.app.controller.admin;

import com.app.controller.admin.dto.BookingDto;
import com.app.controller.admin.request.BookingFindRequest;
import com.app.controller.booking.dto.BookingNoteDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.controller.validator.BookingValidator;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.User;
import com.app.service.booking.BookingService;
import com.app.service.security.SecurityUtils;
import com.app.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;
    private final BookingValidator bookingValidator;
    private final UserService userService;

    @Autowired
    public AdminBookingController(BookingService bookingService, BookingValidator bookingValidator, UserService userService) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity addBooking(@RequestBody BookingRequest bookingAddRequest) {
        Map<String, String> errors = bookingValidator.validateForAdminAdd(bookingAddRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        User user;
        if (StringUtils.isNotBlank(bookingAddRequest.getEmail())) {
            String email = bookingAddRequest.getEmail().toLowerCase().trim();
            user = userService.getOrCreateUser(email);
        } else {
            user = userService.createInternalUser();
        }

        Booking booking = bookingService.book(user.getId(), bookingAddRequest);
        return ResponseEntity.ok(new BookingDto(booking, true));
    }

    @PostMapping(value = "/find")
    @ResponseBody
    public List<BookingDto> findBookings(@RequestBody BookingFindRequest bookingFindRequest) {
        List<Booking> bookings = bookingService.getForDates(bookingFindRequest);
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
    public synchronized ResponseEntity updateBooking(@PathVariable Long id, @RequestBody BookingRequest updateRequest) {
        Map<String, String> errors = bookingValidator.validateForAdminUpdate(id, updateRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Booking updateBooking = bookingService.updateBookingById(id, updateRequest);
        return ResponseEntity.ok(new BookingDto(updateBooking, true));
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
