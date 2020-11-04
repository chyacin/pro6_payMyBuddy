package com.openclassroom.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;



@Entity
public class ProBuddyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    private String name;

    public ProBuddyRole(){ }

    public ProBuddyRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + role_id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return role_id;
    }

    public void setId(int id) {
        this.role_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}