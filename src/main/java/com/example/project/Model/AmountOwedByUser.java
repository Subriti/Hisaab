package com.example.project.Model;

import javax.persistence.Embeddable;

@Embeddable
public class AmountOwedByUser {
    private Double amount;
    private boolean isPaid;
    
    public AmountOwedByUser() {
        super();
    }
    public AmountOwedByUser(Double amount, boolean isPaid) {
        super();
        this.amount = amount;
        this.isPaid = isPaid;
    }
    
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public boolean isPaid() {
        return isPaid;
    }
    public void setisPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}

