package com.openclassroom.service;

import com.openclassroom.model.ProBuddyLogin;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassroom.repositories.LoginRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * The service method which saves a logged in user login history
     * @param user the logged in user
     * @param date the date he logged in
     * @param success if the login was successful or not
     */
    @Override
    @Transactional
    public void createLoginHistory(ProBuddyUser user, Timestamp date, Boolean success) {

        ProBuddyUser proBuddyUser = userRepository.findUserByEmail(user.getEmail());

        Timestamp dateTime= Timestamp.from(Instant.now());

        ProBuddyLogin loginSession = new ProBuddyLogin();
        loginSession.setUser(proBuddyUser);
        loginSession.setDate(dateTime);
        loginSession.setSuccess(success);

        loginRepository.save(loginSession);
    }

    /**
     * The service method which find all logins
     * @return list of all logins
     */
    @Override
    public List<ProBuddyLogin> findAllLogins() {

        List<ProBuddyLogin> logins =loginRepository.findAll();
        return logins;
    }
}
