package com.app.model.booking;

import com.app.model.DaoModel;
import com.app.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking")
public class Booking extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BookingIdGenerator")
    @SequenceGenerator(name="BookingIdGenerator", sequenceName = "booking_id_sequence", allocationSize=1)
    private Long id;

    @ManyToOne
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

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public enum BookingStatus {
        APPROVED,
        PENDING,
        CANCELLED
    }

    private Integer guests;

    @Column(name = "reference", insertable = false, updatable = false)
    private String reference;

    @Column(name = "contact_name")
    private String contactName;

    private String phone;

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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
}
