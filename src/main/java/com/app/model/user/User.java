package com.app.model.user;

import com.app.model.DaoModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usr")
public class User extends DaoModel {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdGenerator")
   @SequenceGenerator(name="UserIdGenerator", sequenceName = "usr_id_sequence", allocationSize=1)
   private Long id;

   private String email;

   private String phone;

   @Column(name = "country_code")
   private String countryCode;

   private boolean verified;

   @Column(name = "registration_date", updatable = false, insertable = false)
   private Date registrationDate;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getCountryCode() {
      return countryCode;
   }

   public void setCountryCode(String countryCode) {
      this.countryCode = countryCode;
   }

   public boolean isVerified() {
      return verified;
   }

   public void setVerified(boolean verified) {
      this.verified = verified;
   }

   public Date getRegistrationDate() {
      return registrationDate;
   }

   public void setRegistrationDate(Date registrationDate) {
      this.registrationDate = registrationDate;
   }
}