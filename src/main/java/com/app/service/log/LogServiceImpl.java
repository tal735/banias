package com.app.service.log;

import com.app.model.booking.Booking;
import com.app.service.notification.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogQueueService logQueueService;

    @Autowired
    public LogServiceImpl(LogQueueService logQueueService) {
        this.logQueueService = logQueueService;
    }

    @Override
    public void logBookingAdded(Booking booking) {
        sendLog(booking.getId(), "Booking Created.");
    }

    @Override
    public void logBookingCancelled(Booking booking) {
        sendLog(booking.getId(), "Booking Cancelled By User.");
    }

    @Override
    public void logBookingModified(Booking booking, List<Diff> diffs) {
        String diffsText = diffs.stream().map(Diff::toString).collect(Collectors.joining("\n"));
        String text = "Booking Modified.\n" + diffsText;
        sendLog(booking.getId(), text);
    }

    private void sendLog(Long id, String text) {
        LogMessage logMessage = new LogMessage(id, text);
        logQueueService.enqueue(logMessage);
    }
}
