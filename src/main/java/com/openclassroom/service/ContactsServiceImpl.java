package com.openclassroom.service;

import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService{


    @Autowired
     private ContactsRepository contactsRepository;
    @Autowired
     private UserService userService;


    /**
     * The service method to add an existing user to the logged in user contacts
     * @param connectorUser the logged in user
     * @param connectedUserEmail the user's email to be connected to
     */
    @Override
    public void createContactsConnection(ProBuddyUser connectorUser, String connectedUserEmail) {
    if((!(connectorUser.getEmail().equals(connectedUserEmail))) && (userService.findUserByEmail(connectedUserEmail) != null)) {
        List<ProBuddyContacts> contactsList = contactsRepository.findAllByFirstUser(connectorUser);
        ProBuddyUser connectedUser = userService.findUserByEmail(connectedUserEmail);
        if(contactsList.isEmpty() || contactsList.stream().noneMatch(proBuddyContacts ->
                userService.findUserByEmail(proBuddyContacts.getSecondUser().getEmail()).equals(connectedUser)));
        ProBuddyContacts newContact = new ProBuddyContacts(connectorUser,connectedUser);
        contactsRepository.save(newContact);
        }
    }

    /**
     * The service method which loads the contact list for the logged in user
     * @param connectorUser the logged in user
     * @return list of the connected users
     */
    @Override
    public List<ProBuddyUser> findConnectedUserByConnectorUser(ProBuddyUser connectorUser) {
        if(connectorUser == null);
        if(userService == null);
           List<ProBuddyUser> connectedUserList = new ArrayList<>();
        if (userService.findUserById(connectorUser.getId()) != null) {
            List<ProBuddyContacts> cList = contactsRepository.findAllByFirstUser(connectorUser);
            for(ProBuddyContacts contacts: cList){
                connectedUserList.add(userService.findUserByEmail(contacts.getSecondUser().getEmail()));
            }
        }

        return connectedUserList;
    }

    /**
     * The service methods which loads contacts where logged in users connects to the other users
     * @param loggedInUser the logged in user
     * @param userToConnectTo the user to be connected to
     * @return the list of existing connection
     */
    @Override
    public List<ProBuddyContacts> findConnectionWithThisUser(ProBuddyUser loggedInUser, ProBuddyUser userToConnectTo) {
        return contactsRepository.findAllByFirstUserAndSecondUser(loggedInUser, userToConnectTo);
    }

}
