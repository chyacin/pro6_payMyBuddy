package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ProBuddyUser, Integer> {

   ProBuddyUser findUserByEmail(String email);

}
