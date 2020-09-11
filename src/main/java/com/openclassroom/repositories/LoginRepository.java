package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<ProBuddyLogin, Integer> {

}
