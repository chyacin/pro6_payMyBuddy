package com.openclassroom.service;


import com.openclassroom.model.ProBuddyRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.openclassroom.repositories.UserRepository;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;



@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    LoginService loginService;


    @Test
    public void loadUserByUserName_returnUserDetails(){

        //arrange
        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("USER");


        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        proBuddyUser.setRoles(role);

        when(userRepository.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        //act
        ProBuddyUserDetails result = userDetailsService.loadUserByUsername(proBuddyUser.getEmail());

        //assert
        assertEquals("buddy@pmb.com", result.getUsername());


    }
}
