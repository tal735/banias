package com.app.service.scheduled;

import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.notification.NotificationService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SendReminderJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendReminderJob.class);

    private final BookingService bookingService;
    private final NotificationService notificationService;

    @Autowired
    public SendReminderJob(BookingService bookingService, NotificationService notificationService) {
        this.bookingService = bookingService;
        this.notificationService = notificationService;
    }

//    @Scheduled(cron = "0 0 3 * * *") // every day at 3AM
    public void sendReminder() {
        try {
            LOGGER.debug("sendReminder: job started.");
            Date tomorrow = DateTime.now().plusDays(1).withMillisOfDay(0).withHourOfDay(2).toDate(); // todo fix 02:00 offset
            List<Booking> bookingList = bookingService.getBookingForReminder(tomorrow);
            LOGGER.debug("sendReminder: sending a reminder for " + bookingList.size() + " bookings.");
            for (Booking booking : bookingList) {
                notificationService.notifyArrivalReminder(booking);
            }
            LOGGER.debug("sendReminder: job finished.");
        } catch (Exception e) {
            LOGGER.error("Exception while sending email reminders.", e);
        }
    }
}
