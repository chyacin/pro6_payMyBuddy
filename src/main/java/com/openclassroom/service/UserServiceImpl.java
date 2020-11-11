package com.openclassroom.service;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @Override
    public void save(ProBuddyUser proBuddyUser) {
        proBuddyUser.setPassword(bCryptPasswordEncoder.encode(proBuddyUser.getPassword()));
        Optional<ProBuddyRole> role = roleRepository.findById(1);

        if (role.get() != null) {
            Set<ProBuddyRole> proBuddyRoleList = new HashSet<>();
            proBuddyRoleList.add(role.get());
            proBuddyUser.setRoles (proBuddyRoleList);
        }

        userRepository.save(proBuddyUser);
    }

    @Override
    public ProBuddyUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public ProBuddyUser findUserById(int id) {
        Optional<ProBuddyUser> proBuddyUserOptional = userRepository.findById(id);
        if(proBuddyUserOptional.isPresent()) {
            ProBuddyUser user = proBuddyUserOptional.get();
            return user;
        }
        return null;
    }

    @Override
    public ProBuddyUser createNewUserByRegistration(ProBuddyUser proBuddyUser) {

        ProBuddyAccount account = new ProBuddyAccount();
        account.setUser(proBuddyUser);
        account.setBalance(0.0);
        accountService.createAccount(account);

        Set<ProBuddyRole> role =  new HashSet<>();
        ProBuddyRole proBuddyRole = roleService.getRoleByName("User");
        role.add(proBuddyRole);


        ProBuddyUser newUser = new ProBuddyUser();
        newUser.setFirstName(proBuddyUser.getFirstName());
        newUser.setLastName(proBuddyUser.getLastName());
        newUser.setAddress(proBuddyUser.getAddress());
        newUser.setEmail(proBuddyUser.getEmail());
        newUser.setAge(proBuddyUser.getAge());
        newUser.setPhone(proBuddyUser.getPhone());
        newUser.setNationalID(proBuddyUser.getNationalID());
        newUser.setAccount(account);
        newUser.setPassword(proBuddyUser.getPassword());
        newUser.setRoles(role);

        return userRepository.save(newUser);
    }


}
