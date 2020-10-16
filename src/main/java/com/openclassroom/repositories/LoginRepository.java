package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<ProBuddyLogin, Integer> {


}
