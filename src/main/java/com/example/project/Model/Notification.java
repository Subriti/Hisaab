package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"notification_id, title, message, data, sender_id, recipient_token, reciever_id"})

@Entity
@Table(name = "Notification")
public class Notification implements Serializable{
	private static final long serialVersionUID = -4132868616593747054L;

	@Id
	@SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_sequence"
    )
	
    @Column(name = "notification_id")
    private int notificationId;
	
    @Column(name = "title")
    private String title;
    
    @Column(name = "message")
    private String message;
    
    @OneToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private User senderId;
    
    @OneToOne
    @JoinColumn(name = "reciever_id", referencedColumnName = "user_id")
    private User recieverId;
    
    
	public Notification() {
		super();
	}

    public Notification(int notificationId, String title, String message, User senderId, User recieverId) {
        super();
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.senderId=senderId;
        this.recieverId= recieverId;
    }

    public Notification(String title, String message, User senderId, User recieverId) {
        super();
        this.title = title;
        this.message = message;
        this.senderId=senderId;
        this.recieverId= recieverId;
    }


    @JsonGetter("notification_id")
	public int getNotificationId() {
		return notificationId;
	}

    @JsonSetter("notification_id")
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
    
    @JsonGetter("title")
    public String getTitle() {
        return title;
    }

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    @JsonSetter("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonGetter("senderId")
    public User getSenderId() {
        return senderId;
    }

    @JsonSetter("senderId")
    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    @JsonGetter("recieverId")
    public User getRecieverId() {
        return recieverId;
    }

    @JsonSetter("recieverId")
    public void setRecieverId(User recieverId) {
        this.recieverId = recieverId;
    }
}
