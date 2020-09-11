package com.openclassroom.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProBuddyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bankName;
    private Integer userID;
    private Integer id;
    private Double balance;

    public ProBuddyAccount() {
    }

    public ProBuddyAccount(String bankName, Integer userID, Integer id, Double balance){
        this.bankName = bankName;
        this.userID = userID;
        this.id = id;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "bankName='" + bankName + '\'' +
                ", userID=" + userID +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
