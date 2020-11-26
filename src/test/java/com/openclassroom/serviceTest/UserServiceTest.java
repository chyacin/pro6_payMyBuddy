package com.openclassroom.serviceTest;


import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.UserRepository;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.service.UserServiceImpl;
import com.openclassroom.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AccountServiceImpl accountService;


   /* @Override
    public void save(ProBuddyUser proBuddyUser) {
        proBuddyUser.setPassword(bCryptPasswordEncoder.encode(proBuddyUser.getPassword()));
        Optional<ProBuddyRole> role = roleRepository.findById(1);

        if (role.get() != null) {
            Set<ProBuddyRole> proBuddyRoleList = new HashSet<>();
            proBuddyRoleList.add(role.get());
            proBuddyUser.setRoles (proBuddyRoleList);
        }

        userRepository.save(proBuddyUser);
    }*/

    @Test
    public void saveUser_returnUser(){
        //arrange
        ProBuddyRole role = new ProBuddyRole();
        role.setId(1);
        role.setName("USER");
        Set<ProBuddyRole> proBuddyRoleList = new HashSet<>();
        proBuddyRoleList.add(role);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        proBuddyUser.setPassword(bCryptPasswordEncoder.encode(proBuddyUser.getPassword()));
        proBuddyUser.setRoles(proBuddyRoleList);


        when()


    }

    @Test
    public void findUserByEmail_returnUserByEmail(){

    }

}
