package com.openclassroom.service;

import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsServiceImpl implements  ContactsService{


    @Autowired
     private ContactsRepository contactsRepository;
     private UserService userService;



    @Override
    public void createContactsConnection(ProBuddyUser connectorUser, String connectedUserEmail) {
    if((!(connectorUser.getEmail().equals(connectedUserEmail))) && (userService.findUserByEmail(connectedUserEmail) != null)) {
        List<ProBuddyContacts> contactsList = contactsRepository.findAllById(connectorUser.getId());
        ProBuddyUser connectedUser = userService.findUserByEmail(connectedUserEmail);
        if(contactsList.isEmpty() || contactsList.stream().noneMatch(proBuddyContacts ->
                userService.findUserById(proBuddyContacts.getSecondUser().getId()).equals(connectedUser)));
        ProBuddyContacts newContact = new ProBuddyContacts(connectorUser,connectedUser);
        contactsRepository.save(newContact);
        }
    }

    @Override
    public List<ProBuddyUser> findConnectedUserByConnectorUser(ProBuddyUser connectorUser) {
           List<ProBuddyUser> connectedUserList = new ArrayList<>();
        if (userService.findUserById(connectorUser.getId()) != null) {
            for(ProBuddyContacts contacts: contactsRepository.findAllById(connectorUser.getId())){
                connectedUserList.add(userService.findUserById(contacts.getSecondUser().getId()));
            }
        }

        return connectedUserList;
    }


}
