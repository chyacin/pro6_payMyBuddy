package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<ProBuddyTransactions, Integer> {

    public List<ProBuddyTransactions> findAllBySenderAccount(ProBuddyAccount senderAccount);
    public List<ProBuddyTransactions> findAllByReceiverAccount(ProBuddyAccount receiverAccount);
    public List<ProBuddyTransactions> findAllBySenderAccountOrReceiverAccount(ProBuddyAccount senderAccount, ProBuddyAccount receiverAccount);

}
