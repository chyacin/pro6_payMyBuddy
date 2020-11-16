package com.openclassroom.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ProBuddyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ProBuddyUser user;

    @NotBlank(message="Please enter your Bank Name")
    private String bankName;
    @NotBlank(message="Please enter your Bank account number")
    private String bankAccountNumber;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProBuddyUser getUser() {
        return user;
    }

    public void setUser(ProBuddyUser user) {
        this.user = user;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ProBuddyAccount{" +
                "id=" + id +
                ", user=" + user +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
