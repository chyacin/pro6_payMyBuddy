package com.openclassroom.serviceTest;


import com.openclassroom.model.ProBuddyRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.openclassroom.repositories.UserRepository;
import com.openclassroom.service.UserDetailsServiceImpl;
import com.openclassroom.service.UserServiceImpl;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        //act
        ProBuddyUserDetails result = userDetailsService.loadUserByUsername(proBuddyUser.getEmail());

        //assert
        assertEquals("buddy@pmb.com", result.getUsername());


    }
}
