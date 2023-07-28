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

@JsonPropertyOrder({ "group_id, group_name, group_admin" })

@Entity
@Table(name = "Groups")
public class Group implements Serializable {

    private static final long serialVersionUID = -1244383128993878686L;

    @Id
    @SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_sequence")

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "group_name")
    private String groupName;

    @OneToOne
    @JoinColumn(name = "group_admin", referencedColumnName = "user_id")
    private User groupAdminUser;

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_at")
    private Date createdAt;

    public Group() {

    }

    public Group(int groupId, String groupName, User groupAdminUser, Date createdAt) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAdminUser = groupAdminUser;
        this.createdAt= createdAt;
    }

    public Group(String groupName, User groupAdminUser, Date createdAt) {
        super();
        this.groupName = groupName;
        this.groupAdminUser = groupAdminUser;
        this.createdAt= createdAt;
    }

    @JsonGetter("group_id")
    public int getGroupId() {
        return groupId;
    }

    @JsonSetter("group_id")
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @JsonGetter("group_name")
    public String getGroupName() {
        return groupName;
    }

    @JsonSetter("group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonGetter("group_admin")
    public User getGroupAdminUser() {
        return groupAdminUser;
    }

    @JsonSetter("group_admin")
    public void setGroupAdminUser(User groupAdminUser) {
        this.groupAdminUser = groupAdminUser;
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
