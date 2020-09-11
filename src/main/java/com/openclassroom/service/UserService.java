package com.openclassroom.service;

import com.openclassroom.model.ProBuddyUser;

public interface UserService {
    void save(ProBuddyUser proBuddyUser);

    ProBuddyUser findByEmail(String email);
}
