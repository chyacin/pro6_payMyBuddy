package com.openclassroom.service;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyLogin;

import java.util.List;

import java.sql.Timestamp;

public interface LoginService {

    public void createLoginHistory(ProBuddyUser user, Timestamp date, Boolean success);

    public List<ProBuddyLogin> findAll();

}
