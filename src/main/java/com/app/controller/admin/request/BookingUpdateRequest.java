package com.app.controller.admin.request;

import com.app.model.booking.Booking;

import java.util.Date;

public class BookingUpdateRequest {
    private Date dateFrom;
    private Date dateTo;
    private Integer guests;
    private Booking.BookingStatus status;
    private String contactName;
    private String phone;
    private Long userId;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
