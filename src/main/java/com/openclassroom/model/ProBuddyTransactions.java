package com.openclassroom.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ProBuddyTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name ="fk_sender_acc_id")
    private int senderAccID;
    @ManyToOne
    @JoinColumn(name ="fk_receiver_acc_id")
    private int receiverAccID;
    @ManyToOne
    @JoinColumn(name ="fk_receiver_user_id")
    private int receiverUserID;
    @ManyToOne
    @JoinColumn(name ="fk_sender_user_id")
    private int senderUserID;

    private String senderName;
    private Double amount;
    private int fee;
    private Timestamp date;

    protected ProBuddyTransactions() {
    }

    public ProBuddyTransactions(int senderAccID, int receiverAccID, int receiverUserID, int senderUserID,
                                String senderName, Double amount, int id, int fee, Timestamp date) {
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

    public int getSenderAccID() {
        return senderAccID;
    }

    public void setSenderAccID(int senderAccID) {
        this.senderAccID = senderAccID;
    }

    public int getReceiverAccID() {
        return receiverAccID;
    }

    public void setReceiverAccID(int receiverAccID) {
        this.receiverAccID = receiverAccID;
    }

    public int getReceiverUserID() {
        return receiverUserID;
    }

    public void setReceiverUserID(int receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public int getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(int senderUserID) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
