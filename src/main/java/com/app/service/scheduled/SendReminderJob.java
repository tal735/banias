package com.app.service.scheduled;

import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.jms.EmailMessage;
import com.app.service.jms.MessageProducer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SendReminderJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendReminderJob.class);

    private final BookingService bookingService;
    private final MessageProducer messageProducer;

    @Autowired
    public SendReminderJob(BookingService bookingService, MessageProducer messageProducer) {
        this.bookingService = bookingService;
        this.messageProducer = messageProducer;
    }

    @Scheduled(cron = "0 0 3 * * *") // every day at 3AM
    public void sendReminder() {
        try {
            LOGGER.debug("sendReminder: job started.");
            Date tomorrow = DateTime.now().plusDays(1).withMillisOfDay(0).withHourOfDay(2).toDate(); // todo fix 02:00 offset
            List<Booking> bookingList = bookingService.getBookingForReminder(tomorrow);
            LOGGER.debug("sendReminder: sending a reminder for " + bookingList.size() + " bookings.");
            for (Booking booking : bookingList) {
                LOGGER.debug("sendReminder: sending a reminder to " + booking.getUser().getEmail() + ". Booking id " + booking.getId());
                EmailMessage emailMessage = new EmailMessage();
                emailMessage.setTo(booking.getUser().getEmail());
                emailMessage.setSubject("We are waiting for you!");
                emailMessage.setText(generateEmailBody(booking));
                messageProducer.sendMessage(MessageProducer.EMAIL_QUEUE_NAME, emailMessage);
            }
            LOGGER.debug("sendReminder: job finished.");
        } catch (Exception e) {
            LOGGER.error("Exception while sending email reminders.", e);
        }
    }

    private String generateEmailBody(Booking booking) {
        String body;

        body = "Hello " + booking.getContactName() + ",";
        body += "\n";
        body += "We are waiting for your arrival tomorrow. " +
                "We wish you a nice stay. Don't hesitate to contact us for any reason.";
        body += "\n";
        body += "Your booking reference number is: " + booking.getReference();

        return body;
    }
}
