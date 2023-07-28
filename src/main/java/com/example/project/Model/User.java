package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({
        "user_id, user_name, name, email, password" })

@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 8139372911386924019L;

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {
        super();
    }

    public User(int userId, String userName, String name, String email, String password) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
       }

   
    public User(String userName, String name, String email, String password, String phoneNumber, String location,
            Date birth_date, Date signupDate, String profilePicture, String fcmToken, Boolean isAdmin, Boolean hideEmail, Boolean hidePhone) {
        super();
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @JsonGetter("user_id")
    public int getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonGetter("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonSetter("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonGetter("password")
    public String getPassword() {
        return password;
    }

    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
