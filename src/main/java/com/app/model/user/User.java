package com.app.model.user;

import com.app.model.DaoModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr")
public class User extends DaoModel {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdGenerator")
   @SequenceGenerator(name="UserIdGenerator", sequenceName = "usr_id_sequence", allocationSize=1)
   private Long id;

   private String email;

   private String password;


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

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public List<String> getRoles() {
      return roles;
   }

   public void setRoles(List<String> roles) {
      this.roles = roles;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}