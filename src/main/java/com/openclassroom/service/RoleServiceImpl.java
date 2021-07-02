package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    /**
     * The service method which saves a role for a user
     * @param proBuddyRole the role to be saved
     */
    @Override
    public void createRole(ProBuddyRole proBuddyRole) {

        if(roleRepository.findByName(proBuddyRole.getName()) == null){
         roleRepository.save(proBuddyRole);
        }
    }

    /**
     * The service method which get the role by name
     * @param name the name of the given role
     * @return the found role name
     */
    @Override
    public ProBuddyRole getRoleByName(String name) {

        return roleRepository.findByName(name);
    }
}
