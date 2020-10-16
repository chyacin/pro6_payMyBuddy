package com.openclassroom.model;

import javax.persistence.*;

@Entity
public class ProBuddyContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_first_user_id")
    private String firstUserId;

    @Column(name ="fk_second_user_id")
    private String secondUserId;

    protected ProBuddyContacts() {
    }

    public ProBuddyContacts(int id, String firstUserId, String secondUserId){
        this.id = id;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", firstUserId='" + firstUserId + '\'' +
                ", secondUserId='" + secondUserId + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(String firstUserId) {
        this.firstUserId = firstUserId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(String secondUserId) {
        this.secondUserId = secondUserId;
    }
}
