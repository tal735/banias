package com.app.model.booking;

import com.app.model.DaoModel;
import com.app.model.user.User;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BookingIdGenerator")
    @SequenceGenerator(name="BookingIdGenerator", sequenceName = "booking_id_sequence", allocationSize=1)
    private Long id;

    @Column(name = "primary_contact")
    private String primaryContact;

    @Column(name = "primary_phone_cc")
    private String primaryPhoneCountryCode;

    @Column(name = "primary_phone_number")
    private String primaryPhoneNumber;

    @Column(name = "primary_email")
    private String primaryEmail;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getPrimaryPhoneCountryCode() {
        return primaryPhoneCountryCode;
    }

    public void setPrimaryPhoneCountryCode(String primaryPhoneCountryCode) {
        this.primaryPhoneCountryCode = primaryPhoneCountryCode;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
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
}
