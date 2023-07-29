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

@JsonPropertyOrder({ "personal_expense_id, category_id, user_id" })

@Entity
@Table(name = "PersonalExpenses")
public class PersonalExpense extends Expense implements Serializable{

    private static final long serialVersionUID = -1244383128993878686L;

    @Id
    @SequenceGenerator(name = "personalExpense_sequence", sequenceName = "personalExpense_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personalExpense_sequence")
    @Column(name = "personal_expense_id")
    private int personalExpenseId;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category categoryId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    public PersonalExpense() {
        super();
    }

    public PersonalExpense(Category categoryId, User userId) {
        super();
        this.categoryId = categoryId;
        this.userId = userId;
    }
    
    @JsonGetter("personal_expense_id")
    public int getPersonalExpenseId() {
        return personalExpenseId;
    }

    @JsonSetter("personal_expense_id")
    public void setPersonalExpenseId(int personalExpenseId) {
        this.personalExpenseId = personalExpenseId;
    }

    @JsonGetter("category_id")
    public Category getCategoryId() {
        return categoryId;
    }

    @JsonSetter("category_id")
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @JsonGetter("user_id")
    public User getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(User userId) {
        this.userId = userId;
    }
}
