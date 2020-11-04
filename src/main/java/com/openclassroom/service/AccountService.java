package com.openclassroom.service;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyUser;

public interface AccountService {

    public ProBuddyAccount createAccount(ProBuddyAccount account);

    public ProBuddyAccount findAccountByUserEmail(String email);

    public ProBuddyAccount findAccountById(int id);

    public void updateAccount(ProBuddyAccount account);
}
