package com.app.controller.booking;

import java.util.Date;

public class BookingRequest {
    private String primaryContactName;
    private String primaryContactPhoneCc;
    private String primaryContactPhoneNumber;
    private String primaryContactEmail;
    private Date dateFrom;
    private Date dateTo;
    private Integer guests;
    private String note;

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactPhoneCc() {
        return primaryContactPhoneCc;
    }

    public void setPrimaryContactPhoneCc(String primaryContactPhoneCc) {
        this.primaryContactPhoneCc = primaryContactPhoneCc;
    }

    public String getPrimaryContactPhoneNumber() {
        return primaryContactPhoneNumber;
    }

    public void setPrimaryContactPhoneNumber(String primaryContactPhoneNumber) {
        this.primaryContactPhoneNumber = primaryContactPhoneNumber;
    }

    public String getPrimaryContactEmail() {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
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
}
