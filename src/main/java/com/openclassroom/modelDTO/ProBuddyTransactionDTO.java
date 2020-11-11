package com.openclassroom.modelDTO;


public class ProBuddyTransactionDTO {

    private int sendingUserID;
    private int receivingUserID;
    private String userName;
    private double amount;
    private double fee;
    private String description;

    public ProBuddyTransactionDTO() {

    }

    public ProBuddyTransactionDTO(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public ProBuddyTransactionDTO(int sendingUserID, int receivingUserID, double amount, double fee, String description) {
        this.sendingUserID = sendingUserID;
        this.receivingUserID = receivingUserID;
        this.amount = amount;
        this.fee = fee;
        this.description = description;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSendingUserID() {
        return sendingUserID;
    }

    public void setSendingUserID(int sendingUserID) {
        this.sendingUserID = sendingUserID;
    }

    public int getReceivingUserID() {
        return receivingUserID;
    }

    public void setReceivingUserID(int receivingUserID) {
        this.receivingUserID = receivingUserID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
