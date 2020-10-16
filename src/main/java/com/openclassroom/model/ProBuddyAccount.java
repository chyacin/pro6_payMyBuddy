package com.openclassroom.model;


import javax.persistence.*;

@Entity
public class ProBuddyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bankName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private int userID;

    private double balance;

    public ProBuddyAccount() {
    }

    public ProBuddyAccount(String bankName, int userID, int id, Double balance){
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
