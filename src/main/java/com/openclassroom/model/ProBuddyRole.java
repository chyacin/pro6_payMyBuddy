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

    @ManyToMany(mappedBy = "proBuddyRole")
  //  @ManyToMany(targetEntity= ProBuddyUser.class)
    //@OneToMany (mappedBy = "proBuddyRole", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProBuddyUser> proBuddyUsers;

    public ProBuddyRole(){

    }

    public ProBuddyRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + role_id +
                ", name='" + name + '\'' +
                ", users=" + proBuddyUsers +
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

    public Set<ProBuddyUser> getProBuddyUsers(){
        return proBuddyUsers;
    }

    public void setProBuddyUsers(Set<ProBuddyUser> proBuddyUsers) {
        this.proBuddyUsers = proBuddyUsers;
    }


}
