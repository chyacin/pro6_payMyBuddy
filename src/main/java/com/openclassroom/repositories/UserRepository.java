package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<ProBuddyUser, Integer> {

   ProBuddyUser findUserByEmail(String email);

    List<ProBuddyUser> findAllById(ProBuddyUser user);
}
