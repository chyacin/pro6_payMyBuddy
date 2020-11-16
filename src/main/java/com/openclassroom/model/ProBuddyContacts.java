package com.openclassroom.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ProBuddyContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private ProBuddyUser firstUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private ProBuddyUser secondUser;

    protected ProBuddyContacts() {
    }

    public ProBuddyContacts(int id, ProBuddyUser firstUser, ProBuddyUser secondUser) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public ProBuddyContacts(ProBuddyUser firstUser, ProBuddyUser secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }


    @Override
    public String toString() {
        return "ProBuddyContacts{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProBuddyUser getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(ProBuddyUser firstUser) {
        this.firstUser = firstUser;
    }

    public ProBuddyUser getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(ProBuddyUser secondUser) {
        this.secondUser = secondUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProBuddyContacts that = (ProBuddyContacts) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
