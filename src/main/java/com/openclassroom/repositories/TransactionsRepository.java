package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<ProBuddyTransactions, Integer> {
}
