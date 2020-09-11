package com.openclassroom.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProBuddyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<ProBuddyUser> proBuddyUsers;

    public ProBuddyRole(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + proBuddyUsers +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProBuddyUser> getUsers(){
        return proBuddyUsers;
    }

    public void setUsers(Set<ProBuddyUser> proBuddyUsers) {
        this.proBuddyUsers = proBuddyUsers;
    }
}
