package com.openclassroom.service;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;

import java.util.List;

public interface TransactionsService {

    public void createTransactionByTransferMoney(int sendingUserID, int receivingUserID,
                                                    Double amount, String description) throws InsufficientBalanceException;

    public List<ProBuddyTransactions> findAll();

    public List<ProBuddyTransactions> findAllByAccount(ProBuddyAccount account);

    public void deposit(ProBuddyUser user, double amount) throws InsufficientBalanceException;

    public void withdraw(ProBuddyUser user, double amount) throws InsufficientBalanceException;
}
