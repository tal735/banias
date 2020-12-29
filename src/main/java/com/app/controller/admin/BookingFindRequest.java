package com.app.controller.admin;

import java.util.Date;

public class BookingFindRequest {
    private Date dateFromMin;
    private Date dateFromMax;
    private Date dateToMin;
    private Date dateToMax;

    public Date getDateFromMin() {
        return dateFromMin;
    }

    public void setDateFromMin(Date dateFromMin) {
        this.dateFromMin = dateFromMin;
    }

    public Date getDateFromMax() {
        return dateFromMax;
    }

    public void setDateFromMax(Date dateFromMax) {
        this.dateFromMax = dateFromMax;
    }

    public Date getDateToMin() {
        return dateToMin;
    }

    public void setDateToMin(Date dateToMin) {
        this.dateToMin = dateToMin;
    }

    public Date getDateToMax() {
        return dateToMax;
    }

    public void setDateToMax(Date dateToMax) {
        this.dateToMax = dateToMax;
    }
}
