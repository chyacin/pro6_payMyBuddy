package com.openclassroom.service;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyUserDTO;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    RoleServiceImpl roleService;

    @Mock
    AccountServiceImpl accountService;

    @Mock
    ProBuddyUserDTO proBuddyUserDTO;

    @Test
    public void saveUser_returnUser() {
        //arrange

       // BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String password =bCryptPasswordEncoder.encode("password");
        ProBuddyRole role = new ProBuddyRole();
        role.setId(1);
        role.setName("USER");
        Set<ProBuddyRole> proBuddyRoleList = new HashSet<>();
        proBuddyRoleList.add(role);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        proBuddyUser.setPassword(password);
        proBuddyUser.setRoles(proBuddyRoleList);


        when(roleRepository.findById(1)).thenReturn(java.util.Optional.of(role));

        //act
        userService.save(proBuddyUser);

        //assert
        verify(userRepository, times(1)).save(any(ProBuddyUser.class));

    }

    @Test
    public void findUserByEmail() {

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("pmb@pmb.com");

        when(userRepository.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        //act
        ProBuddyUser result = userService.findUserByEmail(proBuddyUser.getEmail());

        //assert
        assertNotNull(result);
        assertEquals("pmb@pmb.com", result.getEmail());


    }

    @Test
    public void findAllUsersByEmail(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        List<ProBuddyUser> proBuddyUserList = new ArrayList<>();
        proBuddyUserList.add(proBuddyUser);


        when(userRepository.findAll()).thenReturn(proBuddyUserList);

        //act
        List<ProBuddyUser> result = userService.findAllUsersByEmail(proBuddyUser.getEmail());

        //assert
        assertEquals("buddy@pmb.com", result.get(0).getEmail());
    }

    @Test
    public void findUserById() {

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);

        when(userRepository.findById(proBuddyUser.getId())).thenReturn(java.util.Optional.of(proBuddyUser));

        //act
        ProBuddyUser result = userService.findUserById(proBuddyUser.getId());

        //assert
        assertEquals(1, result.getId());


    }

    @Test
    public void createNewUserByRegistration() {

        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        ProBuddyAccount account = new ProBuddyAccount();
        proBuddyUserDTO.setPassword("password");
        String password = bCryptPasswordEncoder.encode("password");
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setPassword(password);
        ProBuddyRole role = new ProBuddyRole();
        role.setName("USER");
        Set<ProBuddyRole> proBuddyRoleList = new HashSet<>();
        proBuddyRoleList.add(role);


        proBuddyUser.setEmail("buddy@pmb.com");
        proBuddyUser.setRoles(proBuddyRoleList);

        account.setUser(proBuddyUser);
        account.setBankName("Bank of France");
        account.setBankAccountNumber("account 85");
        account.setBalance(0.00);

        when(proBuddyUserDTO.createProBuddyUser()).thenReturn(proBuddyUser);
        when(userRepository.save(proBuddyUser)).thenReturn(proBuddyUser);
        when(roleService.getRoleByName("USER")).thenReturn(role);
       // when(accountService.createAccount(account)).thenReturn(account);

        //act
        ProBuddyUser result = userService.createNewUserByRegistration(proBuddyUserDTO);

        //assert
        assertNotNull(result);
        verify(userRepository, times(2)).save(proBuddyUser);
    }


}