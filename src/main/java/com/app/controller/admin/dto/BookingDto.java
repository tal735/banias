package com.app.controller.admin.dto;

import com.app.model.booking.Booking;

import java.util.Date;

public class BookingDto {
    private Date dateFrom;
    private Date dateTo;
    private Booking.BookingStatus status;
    private Integer guests;
    private String contactName;
    private String phone;

    private Long id;
    private String reference;
    private String email;
    private Long userId;

    public BookingDto(Booking booking, boolean userDetails) {
        this.id = booking.getId();
        this.dateFrom = booking.getDateFrom();
        this.dateTo = booking.getDateTo();
        this.status = booking.getStatus();
        this.guests = booking.getGuests();
        this.contactName = booking.getContactName();
        this.reference = booking.getReference();
        this.phone = booking.getPhone();
        if (userDetails) {
            this.email = booking.getUser().getEmail();
            this.userId = booking.getUser().getId();
        }
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
