package com.app.model.booking;

import com.app.model.DaoModel;
import com.app.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_note")
public class BookingNote extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BookingNoteIdGenerator")
    @SequenceGenerator(name="BookingNoteIdGenerator", sequenceName = "booking_notes_id_sequence", allocationSize=1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;

    private String note;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
