package com.openclassroom.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class ProBuddyLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="fk_login_user_id")
    private Integer userID;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp date;

    @Type(type = "boolean")
    private Boolean success;

    protected ProBuddyLogin() {
    }

    public ProBuddyLogin(Integer id, Integer userID, Timestamp date, Boolean success){
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.success = success;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", userID=" + userID +
                ", date=" + date +
                ", success=" + success +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
