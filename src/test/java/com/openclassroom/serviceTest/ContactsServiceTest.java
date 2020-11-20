package com.openclassroom.serviceTest;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.service.ContactsServiceImpl;
import com.openclassroom.service.UserServiceImpl;
import com.openclassroom.repositories.ContactsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import javax.validation.constraints.Max;
import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ContactsServiceTest {

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

        List<ProBuddyUser> firstUserList = new ArrayList<>();
        firstUserList.add(firstUser);

        Mockito.when(contactsRepository.findAllByFirstUser(firstUser)).thenReturn(new ArrayList<>());
        Mockito.when(userService.findUserByEmail(secondUser.getEmail())).thenReturn(secondUser);

        //act
       contactsService.createContactsConnection(firstUser, secondUser.getEmail());

        Mockito.verify(contactsRepository, times(1)).save(any(ProBuddyContacts.class));

    }

    @Test
    public void findConnectedUserByConnectorUser_returnConnectedUser(){

    }

    @Test
    public void findConnectionWithThisUser_returnConnection(){

    }

}
