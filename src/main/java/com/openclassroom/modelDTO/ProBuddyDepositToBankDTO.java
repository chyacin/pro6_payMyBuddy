package com.openclassroom.modelDTO;

import javax.validation.constraints.NotBlank;

public class ProBuddyDepositToBankDTO {

    private double amount;

    public ProBuddyDepositToBankDTO() {
    }

    public ProBuddyDepositToBankDTO(double amount) {
        this.amount = amount;

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountString(){
        String stringAmount = String.valueOf(amount);
        return stringAmount;
    }
}
