package com.app.controller.booking.dto;

import com.app.model.booking.BookingNote;

public class BookingNoteDto {
    private Long id;
    private String user;
    private String note;

    public BookingNoteDto(BookingNote bookingNote) {
        this.id = bookingNote.getId();
        this.user = bookingNote.getUser().getEmail();
        this.note = bookingNote.getNote();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
