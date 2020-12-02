package com.openclassroom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.model.ProBuddyUser;

import com.openclassroom.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class HomeControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ProBuddyUserDetails user;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testHomeWithLoggedInUser() throws Exception {
        ProBuddyUser user = new ProBuddyUser();
        user.setEmail("aw@pmb.com");

        when(userService.findUserByEmail("aw@pmb.com")).thenReturn(user);

        mockMvc.perform(get("/user/home"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("aw@pmb.com")

    public void testContact() throws Exception {
        ProBuddyUser user = new ProBuddyUser();
        user.setEmail("aw@pmb.com");

        when(userService.findUserByEmail("aw@pmb.com")).thenReturn(user);

        mockMvc.perform(get("/user/contact"))
                .andExpect(status().is2xxSuccessful());
    }

}