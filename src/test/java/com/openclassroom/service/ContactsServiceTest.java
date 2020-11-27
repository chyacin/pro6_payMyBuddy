package com.openclassroom.service;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.repositories.ContactsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class  ContactsServiceTest {

    @InjectMocks
    private ContactsServiceImpl contactsService;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void createContactsConnection_returnConnection(){
       //arrange

        ProBuddyUser firstUser = new ProBuddyUser();
        firstUser.setEmail("firstUser@pmb.com");

        ProBuddyUser secondUser = new ProBuddyUser();
        secondUser.setEmail("secondUser@pmb.com");

        Mockito.when(contactsRepository.findAllByFirstUser(firstUser)).thenReturn(new ArrayList<>());
        Mockito.when(userService.findUserByEmail(secondUser.getEmail())).thenReturn(secondUser);

        //act
       contactsService.createContactsConnection(firstUser, secondUser.getEmail());

        Mockito.verify(contactsRepository, times(1)).save(any(ProBuddyContacts.class));

    }

    @Test
    public void findConnectedUserByConnectorUser_returnConnectedUser(){


        //arrange
        ProBuddyUser loggedInUser = new ProBuddyUser();
        ProBuddyUser userToConnectTo = new ProBuddyUser();

        ProBuddyContacts connectedUsers = new ProBuddyContacts(loggedInUser, userToConnectTo);

        List<ProBuddyContacts> connectedUsersList = new ArrayList<>();
        connectedUsersList.add(connectedUsers);

        Mockito.when(contactsRepository.findAllByFirstUser(loggedInUser)).thenReturn(connectedUsersList);
        Mockito.when(userService.findUserById(loggedInUser.getId())).thenReturn(loggedInUser);

        //act
        List<ProBuddyUser> result = contactsService.findConnectedUserByConnectorUser(loggedInUser);

        //assert
        assertNotNull(result);
        assertEquals(1,result.size());
    }

    @Test
    public void findConnectionWithThisUser_returnConnection(){

        //arrange
        ProBuddyUser loggedInUser = new ProBuddyUser();

        ProBuddyUser userToConnectTo = new ProBuddyUser();

        ProBuddyContacts connectedUsers = new ProBuddyContacts(loggedInUser, userToConnectTo);

        List<ProBuddyContacts> connectedUsersList = new ArrayList<>();
        connectedUsersList.add(connectedUsers);

        Mockito.when(contactsRepository.findAllByFirstUserAndSecondUser(loggedInUser, userToConnectTo)).thenReturn(connectedUsersList);
        //act
        List<ProBuddyContacts> result = contactsService.findConnectionWithThisUser(loggedInUser, userToConnectTo);

        //assert
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(loggedInUser, result.get(0).getFirstUser());
        assertEquals(userToConnectTo, result.get(0).getSecondUser());


    }

}
