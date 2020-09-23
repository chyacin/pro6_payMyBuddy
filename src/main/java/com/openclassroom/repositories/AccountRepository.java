package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<ProBuddyAccount, Integer> {
}
