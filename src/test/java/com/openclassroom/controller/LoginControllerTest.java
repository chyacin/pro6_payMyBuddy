package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.service.LoginService;
import com.openclassroom.service.RoleService;
import com.openclassroom.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class LoginControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private RoleService roleService;

    @Autowired
    WebApplicationContext webContext;


    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

/*
    @Test
    @WithUserDetails("aw@pmb.com")
    public void testHomeWithLoggedInUser() throws Exception {
        ProBuddyUser user = new ProBuddyUser();
        user.setEmail("aw@pmb.com");

        when(userService.findUserByEmail("aw@pmb.com")).thenReturn(user);

        mockMvc.perform(get("/user/home"))
                .andExpect(status().is2xxSuccessful());
    }*/

    @Test
    @WithUserDetails("casm@pb.com")
    public void testGetAdmin() throws Exception{

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("ADMIN");

        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);
        ProBuddyUser user = new ProBuddyUser();
        user.setRoles(role);
        user.setEmail("casm@pb.com");
        when(userService.findUserByEmail("casm@pb.com")).thenReturn(user);

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/admin/loginHistory"));

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testGetUser() throws Exception{

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("USER");

        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);
        ProBuddyUser user = new ProBuddyUser();
        user.setRoles(role);
        user.setEmail("aw@pmb.com");
        when(userService.findUserByEmail("aw@pmb.com")).thenReturn(user);

        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void login() {
    }

    @Test
    public void loginHistory() {
    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void signOut() throws Exception {
        ProBuddyUser user = new ProBuddyUser();
        user.setEmail("aw@pmb.com");
        when(userService.findUserByEmail("aw@pmb.com")).thenReturn(user);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));


    }
}