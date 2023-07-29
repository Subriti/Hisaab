package com.example.project.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"description, amount, created_at" })

@MappedSuperclass
public abstract class Expense{

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_at")
    private Date createdAt;

    public Expense() {
        super();
    }

    public Expense(String description, int amount, Date createdAt) {
        super();
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonGetter("amount")
    public int getAmount() {
        return amount;
    }

    @JsonSetter("amount")
    public void setAmount(int amount) {
        this.amount = amount;
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
