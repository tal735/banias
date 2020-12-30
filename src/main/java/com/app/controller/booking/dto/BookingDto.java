package com.app.controller.booking.dto;

import com.app.model.booking.Booking;

import java.util.Date;

public class BookingDto {
    private Long id;
    private Date dateFrom;
    private Date dateTo;
    private Booking.BookingStatus status;
    private Integer guests;
    private String email;

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.dateFrom = booking.getDateFrom();
        this.dateTo = booking.getDateTo();
        this.status = booking.getStatus();
        this.guests = booking.getGuests();
        this.email = booking.getUser().getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
