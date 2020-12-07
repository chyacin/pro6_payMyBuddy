package com.openclassroom.modelDTO;

import javax.validation.constraints.NotBlank;

public class ProBuddyTransferFormDTO {

    @NotBlank(message="Please enter an email address")
    String connectedUserEmail;
    double amount;
    @NotBlank(message="Please enter a description")
    String description;

    public ProBuddyTransferFormDTO() {
    }

    public ProBuddyTransferFormDTO(String connectedUserEmail, double amount, String description) {
        this.connectedUserEmail = connectedUserEmail;
        this.amount = amount;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
