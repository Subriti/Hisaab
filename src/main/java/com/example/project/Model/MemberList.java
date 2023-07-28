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


@JsonPropertyOrder({"member_id, user_id, group_id"})

@Entity
@Table(name = "Member_List")
public class MemberList implements Serializable{
    
    private static final long serialVersionUID = 8139372911386924019L;

    @Id
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence")

    @Column(name = "member_id")
    private int memberId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User memberUserId;
    
    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group groupId;
    
    
    public MemberList() {
        super();
    }

    public MemberList(int memberId, User memberUserId, Group groupId) {
        super();
        this.memberId = memberId;
        this.memberUserId = memberUserId;
        this.groupId = groupId;
    }

    public MemberList(User memberUserId, Group groupId) {
        super();
        this.memberUserId = memberUserId;
        this.groupId = groupId;
    }

    
    @JsonGetter("member_id")
    public int getMemberId() {
        return memberId;
    }

    @JsonSetter("member_id")
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }


    @JsonGetter("user_id")
    public User getMemberUserId() {
        return memberUserId;
    }

    @JsonSetter("user_id")
    public void setMemberUserId(User memberUserId) {
        this.memberUserId = memberUserId;
    }

    @JsonGetter("group_id")
    public Group getGroupId() {
        return groupId;
    }

    @JsonSetter("group_id")
    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }  
}
