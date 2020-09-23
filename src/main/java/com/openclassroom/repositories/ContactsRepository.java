package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<ProBuddyContacts, Integer> {
}
