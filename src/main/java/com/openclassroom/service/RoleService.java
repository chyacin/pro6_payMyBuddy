package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;

public interface RoleService {

    public void createRole(ProBuddyRole proBuddyRole);

    public ProBuddyRole getRoleByName(String name);


}
