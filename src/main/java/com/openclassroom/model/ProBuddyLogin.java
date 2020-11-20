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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ProBuddyUser user;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp date;

    @Type(type = "boolean")
    private Boolean success;

    public ProBuddyLogin() {
    }

    public ProBuddyLogin(Integer id, ProBuddyUser user, Timestamp date, Boolean success) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.success = success;
    }

    @Override
    public String toString() {
        return "ProBuddyLogin{" +
                "id=" + id +
                ", user=" + user +
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

    public ProBuddyUser getUser() {
        return user;
    }

    public void setUser(ProBuddyUser user) {
        this.user = user;
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
