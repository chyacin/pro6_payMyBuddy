package com.openclassroom.modelDTO;

public class ProBuddyProfileDTO {

    private String firstName;
    private String lastName;
    private String bankName;
    private double balance;
    private String bankAccountNumber;

    public ProBuddyProfileDTO() {
    }

    public ProBuddyProfileDTO(String firstName, String lastName, String bankName, double balance, String bankAccountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankName = bankName;
        this.balance = balance;
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
