package com.openclassroom.service;

import com.openclassroom.model.ProBuddyLogin;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.LoginRepository;
import com.openclassroom.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;



    @Test
    public void createLoginHistory_returnLoginHistory() {

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");

        ProBuddyLogin loginSession = new ProBuddyLogin();

        loginSession.setUser(proBuddyUser);
        loginSession.setDate(Timestamp.from(Instant.now()));
        loginSession.setSuccess(true);

        when(userRepository.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        loginService.createLoginHistory(proBuddyUser, Timestamp.from(Instant.now()), true);

        verify(loginRepository, times(1)).save(any(ProBuddyLogin.class));
    }

    @Test
    public void findAllLogins_returnAllLogins() {

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