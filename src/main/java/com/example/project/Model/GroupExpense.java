package com.example.project.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

@JsonPropertyOrder({ "group_expense_id, group_id, paid_by" })

@Entity
@Table(name = "GroupExpenses")
public class GroupExpense extends Expense implements Serializable{

    private static final long serialVersionUID = -1244383128993878686L;

    @Id
    @SequenceGenerator(name = "groupExpense_sequence", sequenceName = "groupExpense_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupExpense_sequence")
    @Column(name = "group_expense_id")
    private int groupExpenseId;

    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group groupId;
    
    @OneToOne
    @JoinColumn(name = "paid_by", referencedColumnName = "user_id")
    private User paidBy;
    
    @ElementCollection
    @CollectionTable(name = "group_expenses_participants", joinColumns = @JoinColumn(name = "group_expense_id"))
    private List<User> participants;
    
    @ElementCollection
    @CollectionTable(name = "group_expenses_amount_owed", joinColumns = @JoinColumn(name = "group_expense_id"))
    private Map<User, Double> amountOwedByParticipants;

    public GroupExpense() {
        super();
    }

    public GroupExpense(int groupExpenseId, Group groupId, User paidBy) {
        super();
        this.groupExpenseId = groupExpenseId;
        this.groupId = groupId;
        this.paidBy = paidBy;
    }
    
    public GroupExpense(Group groupId, User paidBy) {
        super();
        this.groupId = groupId;
        this.paidBy = paidBy;
    }

    @JsonGetter("group_expense_id")
    public int getGroupExpenseId() {
        return groupExpenseId;
    }

    @JsonSetter("group_expense_id")
    public void setGroupExpenseId(int groupExpenseId) {
        this.groupExpenseId = groupExpenseId;
    }

    @JsonGetter("group_id")
    public Group getGroupId() {
        return groupId;
    }

    @JsonSetter("group_id")
    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }

    @JsonGetter("paid_by")
    public User getPaidBy() {
        return paidBy;
    }

    @JsonSetter("paid_by")
    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }
    
    public List<User> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Map<User, Double> getAmountOwedByParticipants() {
        return amountOwedByParticipants;
    }

    public void setAmountOwedByParticipants(Map<User, Double> amountOwedByParticipants) {
        this.amountOwedByParticipants = amountOwedByParticipants;
    }
}
