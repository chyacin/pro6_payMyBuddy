package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyUserDTO;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ContactsService contactsService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }


    @Test
    public void testGetRegisterProBuddy()throws Exception{

        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void testPostRegisterNewProBuddyUser()throws Exception {

        ProBuddyUser proBuddyUser = new ProBuddyUser();

        ProBuddyUserDTO proBuddyUserDTO = new ProBuddyUserDTO();
        proBuddyUserDTO.setEmail("buddy@pmb.com");
        proBuddyUserDTO.setLastName("Wall");
        proBuddyUserDTO.setFirstName("John");
        proBuddyUserDTO.setPassword("password");
        proBuddyUserDTO.setAddress("55 Fourth Street");
        proBuddyUserDTO.setAge(25);
        proBuddyUserDTO.setBankName("Bank of France");
        proBuddyUserDTO.setBankAccountNumber("Account55");
        proBuddyUserDTO.setNationalID("nationalID");


        when(userService.findUserByEmail(proBuddyUserDTO.getEmail())).thenReturn(null);
        mockMvc.perform(post("/register")
                .requestAttr("user",  proBuddyUserDTO))
                .andExpect(view().name("register"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void addUserConnection() throws Exception{

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("aw@pmb.com");


        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);
        mockMvc.perform(get("/user/addUserConnection"))
                .andExpect(view().name("addUserConnection"))
                .andExpect(status().is2xxSuccessful());
    }



    @Test
    @WithUserDetails("aw@pmb.com")
    public void createAddUserConnection() throws Exception{

        ProBuddyUser loggedInUser = new ProBuddyUser();
        loggedInUser.setId(1);
        loggedInUser.setEmail("aw@pmb.com");

        ProBuddyUser connectedUser = new ProBuddyUser();
        connectedUser.setId(2);
        connectedUser.setEmail("jd@pmb.com");


        when(userService.findUserByEmail(loggedInUser.getEmail())).thenReturn(loggedInUser);
        when(userService.findUserByEmail(connectedUser.getEmail())).thenReturn(connectedUser);
        when(contactsService.findConnectionWithThisUser(loggedInUser, connectedUser)).thenReturn(new ArrayList<>());


        mockMvc.perform(post("/user/addUserConnection")
                .queryParam("connectedUserEmail", connectedUser.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/user/makeTransfer"));
    }

}