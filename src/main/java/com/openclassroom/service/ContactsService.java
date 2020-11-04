package com.openclassroom.service;

import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;

import java.util.List;

public interface ContactsService {

    public void createContactsConnection(ProBuddyUser connectorUser, String connectedUserEmail);

    public List<ProBuddyUser> findConnectedUserByConnectorUser(ProBuddyUser connectorUser);


}
