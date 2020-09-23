package com.openclassroom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProBuddyRoleProBuddyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolesId;
    private Integer proBuddyUsersId;

    public ProBuddyRoleProBuddyUser() {
    }

    public ProBuddyRoleProBuddyUser(Integer rolesId, Integer proBuddyUsersId) {
        this.rolesId = rolesId;
        this.proBuddyUsersId = proBuddyUsersId;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public Integer getProBuddyUsersId() {
        return proBuddyUsersId;
    }

    public void setProBuddyUsersId(Integer proBuddyUsersId) {
        this.proBuddyUsersId = proBuddyUsersId;
    }
}


