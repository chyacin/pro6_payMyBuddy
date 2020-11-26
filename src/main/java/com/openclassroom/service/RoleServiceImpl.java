package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createRole(ProBuddyRole proBuddyRole) {

        if(roleRepository.findByName(proBuddyRole.getName()) == null){
         roleRepository.save(proBuddyRole);
        }
    }
    @Override
    public ProBuddyRole getRoleByName(String name) {

        return roleRepository.findByName(name);
    }
}
