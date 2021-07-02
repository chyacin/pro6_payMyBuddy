package com.openclassroom.controller;

import com.openclassroom.model.*;
import com.openclassroom.modelDTO.ProBuddyLoginDTO;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @WithUserDetails("casm@pb.com")
    public void testAdmin() throws Exception{

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("ADMIN");

        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);
        ProBuddyUser user = new ProBuddyUser();
        user.setRoles(role);
        user.setEmail("casm@pb.com");
        when(userService.findUserByEmail("casm@pb.com")).thenReturn(user);

        mockMvc.perform(get("/admin"))
                .andExpect(view().name("loginHistory"));

    }

    @Test
    public void testLogin() throws Exception{

        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("casm@pb.com")
    public void loginHistory() throws Exception{

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("ADMIN");

        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("casm@pb.com");
        proBuddyUser.setRoles(role);

        ProBuddyLogin proBuddyLogin = new ProBuddyLogin();
        proBuddyLogin.setUser(proBuddyUser);
        proBuddyLogin.setDate(Timestamp.from(Instant.now()));
        proBuddyLogin.setSuccess(true);

        List<ProBuddyLogin> proBuddyLoginList = new ArrayList<>();
        proBuddyLoginList.add(proBuddyLogin);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);
        when(loginService.findAllLogins()).thenReturn(proBuddyLoginList);

        mockMvc.perform(get("/admin/loginHistory"))
                .andExpect(view().name("loginHistory"))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithUserDetails("aw@pmb.com")
    public void testAccessDenied() throws Exception{

        ProBuddyRole proBuddyRole = new ProBuddyRole();
        proBuddyRole.setName("USER");

        Set<ProBuddyRole> role = new HashSet<>();
        role.add(proBuddyRole);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setRoles(role);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        mockMvc.perform(get("/admin/loginHistory"))
                .andExpect(view().name("accessDenied"))
                .andExpect(status().is2xxSuccessful());


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