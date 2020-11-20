package com.openclassroom.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.openclassroom.model.ProBuddyLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public interface LoginRepository extends JpaRepository<ProBuddyLogin, Integer>{



}
