package com.openclassroom.modelDTO;

public class ProBuddyTransferFormDTO {

    String connectedUserEmail;
    double amount;

    public ProBuddyTransferFormDTO() {
    }

    public ProBuddyTransferFormDTO(String connectedUserEmail, double amount) {
        this.connectedUserEmail = connectedUserEmail;
        this.amount = amount;
    }

    public String getConnectedUserEmail() {
        return connectedUserEmail;
    }

    public void setConnectedUserEmail(String connectedUserEmail) {
        this.connectedUserEmail = connectedUserEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
