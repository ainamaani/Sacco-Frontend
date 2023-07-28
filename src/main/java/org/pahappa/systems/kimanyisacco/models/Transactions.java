package org.pahappa.systems.kimanyisacco.models;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="transactions")
public class Transactions {
    private int id;

    private String transactionType;

    private double amount;


    private String saccoMember;


    private LocalDateTime createdOn;

    private double accountBalance = 0.0;

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "transactionType", nullable = false, length = 255)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "amount", nullable = false, length = 255)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSaccoMember() {
        return saccoMember;
    }

    public void setSaccoMember(String saccoMember) {
        this.saccoMember = saccoMember;
    }

    @CreationTimestamp
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "accountBalance", nullable = false, length = 255)
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
