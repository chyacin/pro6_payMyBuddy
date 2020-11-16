package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ContactsRepository extends JpaRepository<ProBuddyContacts, Integer> {

    public List<ProBuddyContacts> findAllByFirstUser(ProBuddyUser user);

    public List<ProBuddyContacts> findAllByFirstUserAndSecondUser(ProBuddyUser loggedInUser, ProBuddyUser userToConnectTo);
}
