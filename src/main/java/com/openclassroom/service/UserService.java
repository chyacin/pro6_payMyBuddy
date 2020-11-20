package com.openclassroom.service;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyUserDTO;

import java.util.List;

public interface UserService {

    void save(ProBuddyUser proBuddyUser);

    ProBuddyUser findUserByEmail(String email);

    List<ProBuddyUser> findAllUsersByEmail(String email);

    public ProBuddyUser findUserById(int id);

    public ProBuddyUser createNewUserByRegistration(ProBuddyUserDTO proBuddyUserDTO);



}
