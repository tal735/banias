package com.app.model.password;

import com.app.model.DaoModel;
import com.app.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "password_reset")
public class PasswordReset extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PasswordResetIdGenerator")
    @SequenceGenerator(name="PasswordResetIdGenerator", sequenceName = "password_reset_id_sequence", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private Boolean used;

    @Column(name = "date_created", insertable = false, updatable = false)
    private Date dateCreated;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
