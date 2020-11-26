package com.openclassroom.serviceTest;


import com.openclassroom.model.ProBuddyLogin;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.service.LoginServiceImpl;
import com.openclassroom.repositories.LoginRepository;
import com.openclassroom.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.ArrayList;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;



@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserServiceImpl userService;


/*
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
    }*/

    @Test
    public void createLoginHistory_returnLoginHistory(){

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");

        ProBuddyLogin loginSession = new ProBuddyLogin();

        loginSession.setUser(proBuddyUser);
        loginSession.setDate(Timestamp.from(Instant.now()));
        loginSession.setSuccess(true);

        when(loginRepository.save(loginSession)).thenReturn(loginSession);
        when(userService.findUserByEmail("buddy@pmb.com")).thenReturn(proBuddyUser);

        loginService.createLoginHistory(proBuddyUser, Timestamp.from(Instant.now()), true);

        verify(loginRepository, times(1)).save(any(ProBuddyLogin.class));
    }

    @Test
    public void findAllLogins_returnAllLogins(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        ProBuddyLogin proBuddyLogin = new ProBuddyLogin();

        proBuddyLogin.setUser(proBuddyUser);

        List<ProBuddyLogin> proBuddyLoginList = new ArrayList<>();
        proBuddyLoginList.add(proBuddyLogin);

        when(loginRepository.findAll()).thenReturn(proBuddyLoginList);

        //act
        List<ProBuddyLogin> result = loginService.findAllLogins();

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());

    }

}
