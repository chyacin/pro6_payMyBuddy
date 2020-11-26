package com.openclassroom.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.openclassroom.service.RoleServiceImpl;
import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.repositories.RoleRepository;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;


    @Test
    public void createRole_returnRole(){
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
    public void findRoleByName_returnRoleByName(){

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
}
