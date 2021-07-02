package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.repositories.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;


    @Test
    public void createUserRole_returnUserRole(){
        //arrange

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("USER");

        when(roleRepository.save(proBuddyRole)).thenReturn(proBuddyRole);

        //act
        roleService.createRole(proBuddyRole);

        //assert
        Mockito.verify(roleRepository, times(1)).save(any(ProBuddyRole.class));

    }

    @Test
    public void createAdminRole_returnAdminRole(){
        //arrange

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("ADMIN");

        when(roleRepository.save(proBuddyRole)).thenReturn(proBuddyRole);

        //act
        roleService.createRole(proBuddyRole);

        //assert
        Mockito.verify(roleRepository, times(1)).save(any(ProBuddyRole.class));

    }

    @Test
    public void findRoleByUserName_returnRoleByName(){

        //arrange
        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("USER");

        when(roleRepository.findByName(proBuddyRole.getName())).thenReturn(proBuddyRole);

        //act
        ProBuddyRole result = roleService.getRoleByName("USER");

        //assert
        assertNotNull(result);
        assertEquals("USER", result.getName());


    }

    @Test
    public void findRoleByAdminName_returnRoleByName(){

        //arrange
        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("ADMIN");

        when(roleRepository.findByName(proBuddyRole.getName())).thenReturn(proBuddyRole);

        //act
        ProBuddyRole result = roleService.getRoleByName("ADMIN");

        //assert
        assertNotNull(result);
        assertEquals("ADMIN", result.getName());


    }
}
