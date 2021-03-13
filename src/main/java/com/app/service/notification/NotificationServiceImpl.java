package com.app.service.notification;

import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.User;
import com.app.service.email.EmailDomainUtils;
import com.app.service.notification.diff.Diff;
import com.app.service.email.EmailQueueService;
import com.app.service.email.EmailMessage;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final EmailQueueService emailQueueService;

    @Autowired
    public NotificationServiceImpl(EmailQueueService emailQueueService) {
        this.emailQueueService = emailQueueService;
    }

    @Override
    public void notifyBookingAdded(Booking booking) {
        User user = booking.getUser();

        String html = "Hello " + booking.getContactName() + "\n" + "Booking Reference: " + booking.getReference() + "\nYou will be notified when your booking is examined and approved.";
        sendEmail(user.getEmail(), "Your Booking Details", html);

        String adminHtml = "New Booking Added By: " + booking.getContactName() + "\n" + "Booking Reference: " + booking.getReference() + "\n" + "Phone Number: " + booking.getPhone();
        sendEmailToAdmin("New Booking Added", adminHtml);
    }

    @Override
    public void notifyBookingCancelled(Booking booking) {
        User user = booking.getUser();

        String subject = "Booking Cancellation [" + booking.getReference() + "]";
        String html = "Booking Reference: " + booking.getReference() + " has been cancelled.";
        sendEmail(user.getEmail(), subject, html);
        sendEmailToAdmin(subject, html);
    }

    @Override
    public void notifyBookingModified(Booking booking, List<Diff> diffs) {
        if (!CollectionUtils.isEmpty(diffs)) {
            String changes = diffs.stream().map(Diff::toString).collect(Collectors.joining("\n"));
            String subject = "Booking Modified [" + booking.getReference() + "]";
            String html = "Booking Reference: " + booking.getReference() + " has been modified.\nHere are the changes:\n" + changes;
            sendEmail(booking.getUser().getEmail(), subject, html);
            sendEmailToAdmin(subject, html);
        }
    }

    @Override
    public void notifyNoteAdded(BookingNote bookingNote) {
        Booking booking = bookingNote.getBooking();
        User noteUser = bookingNote.getUser();
        User bookingUser = booking.getUser();

        int maxWidth = 100;
        String note = bookingNote.getNote();
        if (bookingNote.getNote().length() > maxWidth) {
            note = StringUtils.truncate(note, maxWidth) + "...";
        }
        String html = "A new message has been added to booking " + booking.getReference() + ":\n" + "\"" + note + "\"";
        String subject = booking.getReference() + ": A new message from " + noteUser.getEmail();
        if (noteUser.getId().equals(bookingUser.getId())) {
            sendEmailToAdmin(subject, html);
        } else {
            sendEmail(bookingUser.getEmail(), subject, html); // when admin adds a note, notify the booking user
        }
    }

    @Override
    public void notifyOtp(String email, String otp) {
        String subject = "Your OTP";
        String html = "Your OTP is: " + otp;
        sendEmail(email, subject, html);
    }

    @Override
    public void notifyArrivalReminder(Booking booking) {
        String email = booking.getUser().getEmail();
        LOGGER.debug("sendReminder: sending a reminder to " + email + ". Booking id " + booking.getId());

        String body;
        body = "Hello " + booking.getContactName() + ",";
        body += "\n";
        body += "We are waiting for your arrival tomorrow. " +
                "We wish you a nice stay. Don't hesitate to contact us for any reason.";
        body += "\n";
        body += "Your booking reference number is: " + booking.getReference();

        sendEmail(email, "We are waiting for you!", body);
    }

    private void sendEmail(String to, String subject, String text) {
        sendEmail(Lists.newArrayList(to), subject, text);
    }

    private void sendEmailToAdmin(String subject, String text) {
        sendEmail(EmailDomainUtils.ADMIN_MAILING_LIST, subject, text);
    }

    private void sendEmail(List<String> to, String subject, String text) {
        EmailMessage emailMessage = new EmailMessage(to, subject, text);
        emailQueueService.enqueue(emailMessage);
    }
}
