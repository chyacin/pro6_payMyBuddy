package com.openclassroom.modelDTO;

import javax.validation.constraints.NotBlank;

public class ProBuddyWithdrawFromBankDTO {

    private double amount;


    public ProBuddyWithdrawFromBankDTO() {
    }

    public ProBuddyWithdrawFromBankDTO(double amount) {
        this.amount = amount;
        String stringAmount = String.valueOf(amount);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
