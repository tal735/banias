package com.app.controller.admin.request;

import java.util.Date;

public class BookingFindRequest {
    private Date dateFromMin;
    private Date dateFromMax;
    private Date dateToMin;
    private Date dateToMax;
    private Integer offset;

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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
