package com.openclassroom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProBuddyContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstUserId;
    private String secondUserId;

    protected ProBuddyContacts() {
    }

    public ProBuddyContacts(Integer id, String firstUserId, String secondUserId){
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
