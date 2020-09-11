package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<ProBuddyTransactions, Integer> {
}
