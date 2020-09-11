package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<ProBuddyUser, Integer> {

   ProBuddyUser getUserByEmail(@Param("email") String email);
}
