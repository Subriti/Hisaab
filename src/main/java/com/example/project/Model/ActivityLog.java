package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"log_id, message, created_at, user_id"})

@Entity
@Table(name = "ActivityLog")
public class ActivityLog implements Serializable{
	private static final long serialVersionUID = -4132868616593747054L;

	@Id
	@SequenceGenerator(
            name = "log_sequence",
            sequenceName = "log_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "log_sequence"
    )
	
    @Column(name = "log_id")
    private int logId;
	
    @Column(name = "message")
    private String message;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;
  
    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_at")
    private Date createdAt;
    
	public ActivityLog() {
		super();
	}

    public ActivityLog(int logId, String message, User userId, Date createdAt) {
        super();
        this.logId = logId;
        this.message = message;
        this.userId=userId;
        this.createdAt= createdAt;
    }

    public ActivityLog(String message, User userId, Date createdAt) {
        super();
        this.message = message;
        this.userId=userId;
        this.createdAt= createdAt;
    }


    @JsonGetter("log_id")
	public int getLogId() {
		return logId;
	}

    @JsonSetter("log_id")
	public void setLogId(int logId) {
		this.logId = logId;
	}

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    @JsonSetter("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonGetter("user_id")
    public User getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(User userId) {
        this.userId = userId;
    }
    
    @JsonGetter("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonSetter("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
