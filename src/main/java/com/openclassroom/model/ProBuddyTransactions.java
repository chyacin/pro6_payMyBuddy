package com.openclassroom.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ProBuddyTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private ProBuddyAccount senderAccount;
    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private ProBuddyAccount receiverAccount;
    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private ProBuddyUser receiver;
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private ProBuddyUser sender;

    private String description;
    private double amount;
    private double fee;
    private Timestamp date;

    public ProBuddyTransactions() {
    }

    public ProBuddyTransactions(ProBuddyAccount senderAccount, ProBuddyAccount receiverAccount, ProBuddyUser receiver,
                                ProBuddyUser sender, String description, double amount, double fee, Timestamp date) {

        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.receiver = receiver;
        this.sender = sender;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
    }



    @Override
    public String toString() {
        return "ProBuddyTransactions{" +
                "id=" + id +
                ", senderAccount=" + senderAccount +
                ", receiverAccount=" + receiverAccount +
                ", receiver=" + receiver +
                ", sender=" + sender +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProBuddyAccount getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(ProBuddyAccount senderAccount) {
        this.senderAccount = senderAccount;
    }

    public ProBuddyAccount getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(ProBuddyAccount receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public ProBuddyUser getReceiver() {
        return receiver;
    }

    public void setReceiver(ProBuddyUser receiver) {
        this.receiver = receiver;
    }

    public ProBuddyUser getSender() {
        return sender;
    }

    public void setSender(ProBuddyUser sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
