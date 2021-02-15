package com.app.model.log;


import com.app.model.DaoModel;
import com.app.model.booking.Booking;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_log")
public class BookingLog extends DaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BookingLogIdGenerator")
    @SequenceGenerator(name="BookingLogIdGenerator", sequenceName = "booking_log_id_sequence", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private Date date;

    private String log;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
