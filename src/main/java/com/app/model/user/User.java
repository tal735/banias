package com.app.model.user;

import com.app.model.DaoModel;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usr")
public class User extends DaoModel {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdGenerator")
   @SequenceGenerator(name="UserIdGenerator", sequenceName = "usr_id_sequence", allocationSize=1)
   private Long id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   private String email;

   private String phone;

   @Column(name = "country_code")
   private String countryCode;

   private boolean verified;

   @Column(name = "registration_date", updatable = false, insertable = false)
   private Date registrationDate;

   private String password;

   @Column(name = "admin_notes")
   private String adminNotes;

   private String type;

   @ElementCollection
   @CollectionTable(name="usr_role", joinColumns=@JoinColumn(name="user_id"))
   @Column(name="role")
   private List<String> roles;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
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

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getAdminNotes() {
      return adminNotes;
   }

   public void setAdminNotes(String adminNotes) {
      this.adminNotes = StringUtils.left(adminNotes, 1024);
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public List<String> getRoles() {
      return roles;
   }

   public void setRoles(List<String> roles) {
      this.roles = roles;
   }
}