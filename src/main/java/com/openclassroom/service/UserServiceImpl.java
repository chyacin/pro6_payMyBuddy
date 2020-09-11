package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(ProBuddyUser proBuddyUser) {
        proBuddyUser.setPassword(bCryptPasswordEncoder.encode(proBuddyUser.getPassword()));
        Optional<ProBuddyRole> role = roleRepository.findById(1);

        if (role.get() != null) {
            List<ProBuddyRole> proBuddyRoleList = new ArrayList<>();
            proBuddyRoleList.add(role.get());
            proBuddyUser.setRoles(proBuddyRoleList);
        }

        userRepository.save(proBuddyUser);
    }

    @Override
    public ProBuddyUser findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
