package com.openclassroom.service;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyTransactions;

import java.util.List;

public interface TransactionsService {

    public void createTransactionByTransferMoney(int sendingUserID, int receivingUserID,
                                                    Double amount, String description) throws InsufficientBalanceException;

    public List<ProBuddyTransactions> findAll();
}
