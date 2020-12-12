package com.app.model.booking;

import com.app.model.DaoModel;
import com.app.model.user.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BookingIdGenerator")
    @SequenceGenerator(name="BookingIdGenerator", sequenceName = "booking_id_sequence", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "date_created", insertable = false, updatable = false)
    private Date dateCreated;

    @Column(name = "date_modified")
    private Date dateModified;

    private String status;

    private Integer guests;

    @OneToMany(mappedBy = "booking")
    private List<BookingNote> notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public List<BookingNote> getNotes() {
        return notes;
    }

    public void setNotes(List<BookingNote> notes) {
        this.notes = notes;
    }

    public void addNote(User user, String note) {
        if (StringUtils.isBlank(note)) {
            return;
        }
        if (notes == null) {
            notes = Lists.newArrayList();
        }
        BookingNote bookingNote = new BookingNote();
        bookingNote.setNote(note);
        bookingNote.setUser(user);
        bookingNote.setDate(new Date());
        bookingNote.setBooking(this);
        notes.add(bookingNote);
    }
}
