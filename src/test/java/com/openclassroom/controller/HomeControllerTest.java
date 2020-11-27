package com.openclassroom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
//@SpringBootTest()
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ProBuddyUserDetails user;

    @Mock
    ModelAndView modelAndView;

    @Autowired
    WebApplicationContext webContext;

    @Autowired
    org.springframework.security.core.userdetails.User loggedUser;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Before
    public Authentication setupMockAuthentication() {


        user = new ProBuddyUserDetails(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     //   loggedUser = authentication.getName(user);

        return authentication;
    }

   /* @Test
    @WithMockUser("buddy@pmb.com")
    public void testHomeWithLoggedInUser() throws Exception {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(setupMockAuthentication());
        when(user.getUsername()).thenReturn("buddy@pmb.com");
        when(userService.findUserByEmail("buddy@pmb.com")).thenReturn();

        mockMvc.perform(get("user/home"))
                .andExpect(status().is2xxSuccessful());

    }*/

    @Test
    public void testContact() throws Exception{
    }
}