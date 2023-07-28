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

@JsonPropertyOrder({ "message_id, message_body, timestamp, sender_user_id, reciever_user_id, chat_room_id" })

@Entity
@Table(name = "Message")
public class Message implements Serializable {

	private static final long serialVersionUID = 3412733841802158319L;

	@Id
	@SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )

	@Column(name = "message_id")
	private int messageId;

	@Column(name = "message_body")
    private String messageBody;
	
	@Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "timestamp")
    private Date timestamp;
	
	@OneToOne
	@JoinColumn(name = "sender_user_id", referencedColumnName = "user_id")
	private User senderUserId;

	@OneToOne
	@JoinColumn(name = "reciever_user_id", referencedColumnName = "user_id")
	private User recieverUserId;
	
	@Column(name = "chat_room_id")
    private String chatRoomId; //Sarthak+Subriti jasto

	public Message() {
		super();
	}

	public Message(int messageId, String messageBody, Date timestamp, User senderUserId, User recieverUserId, String chatRoomId) {
        super();
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
        this.senderUserId = senderUserId;
        this.recieverUserId = recieverUserId;
        this.chatRoomId=chatRoomId;
    }

    public Message(String messageBody, Date timestamp, User senderUserId, User recieverUserId, String chatRoomId) {
        super();
        this.messageBody = messageBody;
        this.timestamp = timestamp;
        this.senderUserId = senderUserId;
        this.recieverUserId = recieverUserId;
        this.chatRoomId=chatRoomId;
    }

    @JsonGetter("message_id")
	public int getMessageId() {
		return messageId;
	}
    
    @JsonSetter("message_id")
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	@JsonGetter("message_body")
    public String getMessageBody() {
        return messageBody;
    }
	@JsonSetter("message_body")
    public void setMessagebody(String messageBody) {
        this.messageBody = messageBody;
    }
	
	@JsonGetter("timestamp")
    public Date getTimestamp() {
        return timestamp;
    }
	
    @JsonSetter("timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
	@JsonGetter("reciever_user_id")
	public User getRecieverUserId() {
		return recieverUserId;
	}
	
	@JsonSetter("reciever_user_id")
	public void setRecieverUserId(User recieverUserId) {
		this.recieverUserId = recieverUserId;
	}
	
	@JsonGetter("sender_user_id")
    public User getSenderUserId() {
        return senderUserId;
    }
    
    @JsonSetter("sender_user_id")
    public void setSenderUserId(User senderUserId) {
        this.senderUserId = senderUserId;
    }
    
    @JsonGetter("chat_room_id")
    public String getChatRoomId() {
        return chatRoomId;
    }
    
    @JsonSetter("chat_room_id")
    public void getChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
