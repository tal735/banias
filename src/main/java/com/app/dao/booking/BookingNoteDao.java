package com.app.dao.booking;

import com.app.model.booking.BookingNote;

import java.util.List;

public interface BookingNoteDao {
    void saveOrUpdate(BookingNote bookingNote);

    List<BookingNote> getNotes(Long bookingId, Long offset);
}
