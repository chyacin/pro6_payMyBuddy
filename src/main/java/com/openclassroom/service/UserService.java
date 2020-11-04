package com.openclassroom.service;

import com.openclassroom.model.ProBuddyUser;

public interface UserService {

    void save(ProBuddyUser proBuddyUser);

    ProBuddyUser findUserByEmail(String email);

    public ProBuddyUser findUserById(int id);

    public ProBuddyUser createNewUserByRegistration(ProBuddyUser proBuddyUser);

}
