package com.openclassroom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ProBuddyTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer senderAccID;
    private Integer receiverAccID;
    private Integer receiverUserID;
    private Integer senderUserID;
    private String senderName;
    private Double amount;
    private Integer fee;
    private Timestamp date;

    protected ProBuddyTransactions() {
    }

    public ProBuddyTransactions(Integer senderAccID, Integer receiverAccID, Integer receiverUserID, Integer senderUserID,
                                String senderName, Double amount, Integer id, Integer fee, Timestamp date) {
        this.senderAccID = senderAccID;
        this.receiverAccID = receiverAccID;
        this.receiverUserID = receiverUserID;
        this.senderUserID = senderUserID;
        this.senderName = senderName;
        this.amount = amount;
        this.id = id;
        this.fee = fee;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "senderAccID=" + senderAccID +
                ", receiverAccID=" + receiverAccID +
                ", receiverUserID=" + receiverUserID +
                ", senderUserID=" + senderUserID +
                ", senderName='" + senderName + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                ", fee=" + fee +
                ", date=" + date +
                '}';
    }

    public Integer getSenderAccID() {
        return senderAccID;
    }

    public void setSenderAccID(Integer senderAccID) {
        this.senderAccID = senderAccID;
    }

    public Integer getReceiverAccID() {
        return receiverAccID;
    }

    public void setReceiverAccID(Integer receiverAccID) {
        this.receiverAccID = receiverAccID;
    }

    public Integer getReceiverUserID() {
        return receiverUserID;
    }

    public void setReceiverUserID(Integer receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public Integer getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(Integer senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
