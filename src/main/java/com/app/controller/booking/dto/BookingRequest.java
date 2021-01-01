package com.app.controller.booking.dto;

import com.app.model.booking.Booking;

import java.util.Date;

public class BookingRequest {
    private Date dateFrom;
    private Date dateTo;
    private Integer guests;
    private String note;
    private Booking.BookingStatus status;
    private String contactName;
    private String email;

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Booking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(Booking.BookingStatus status) {
        this.status = status;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
