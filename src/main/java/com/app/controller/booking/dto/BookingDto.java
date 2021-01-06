package com.app.controller.booking.dto;

import com.app.model.booking.Booking;

import java.util.Date;

public class BookingDto {
    private Date dateFrom;
    private Date dateTo;
    private Booking.BookingStatus status;
    private Integer guests;
    private String contactName;
    private String reference;
    private String phone;

    public BookingDto(Booking booking) {
        this.dateFrom = booking.getDateFrom();
        this.dateTo = booking.getDateTo();
        this.status = booking.getStatus();
        this.guests = booking.getGuests();
        this.contactName = booking.getContactName();
        this.reference = booking.getReference();
        this.phone = booking.getPhone();
    }

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

    public Booking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(Booking.BookingStatus status) {
        this.status = status;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
