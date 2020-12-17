package com.openclassroom.service;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyUserDTO;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    //private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * The service method which saves the user
     * @param proBuddyUser the user that was saved
     */
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

    /**
     * The service method which finds the user by email
     * @param email the email of the user
     * @return the found user
     */
    @Override
    public ProBuddyUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * The service method which finds all users by their emails
     * @param email the email of the user
     * @return List of all users
     */
    @Override
    public List<ProBuddyUser> findAllUsersByEmail(String email) {
      List<ProBuddyUser> userList = userRepository.findAll().stream().filter(pro -> pro.getEmail().equals(email))
              .collect(Collectors.toList());
      return userList;
    }


    /**
     * The service method which find user by id
     * @param id the id of the user
     * @return the found user
     */
    @Override
    public ProBuddyUser findUserById(int id) {
        Optional<ProBuddyUser> proBuddyUserOptional = userRepository.findById(id);
        if(proBuddyUserOptional.isPresent()) {
            ProBuddyUser user = proBuddyUserOptional.get();
            return user;
        }
        return null;
    }

    /**
     * The service method which saves a new registered user
     * @param proBuddyUserDTO the data transfer object which contains the registered user information
     * @return the object is the saved user
     */
    @Override
    public ProBuddyUser createNewUserByRegistration(ProBuddyUserDTO proBuddyUserDTO) {

        ProBuddyAccount account = new ProBuddyAccount();
        // create proBuddyUser from DTO
        ProBuddyUser proBuddyUser = proBuddyUserDTO.createProBuddyUser();
        String password = new BCryptPasswordEncoder().encode(proBuddyUser.getPassword());
        account.setBalance(0.00);

        Set<ProBuddyRole> role =  new HashSet<>();
        ProBuddyRole proBuddyRole = roleService.getRoleByName("USER");
        role.add(proBuddyRole);

        proBuddyUser.setRoles(role);
        proBuddyUser.setPassword(password);

        ProBuddyUser savedUser = userRepository.save(proBuddyUser);

        account.setUser(savedUser);
        account.setBankName(proBuddyUserDTO.getBankName());
        account.setBankAccountNumber(proBuddyUserDTO.getBankAccountNumber());
        ProBuddyAccount savedAccount = accountService.createAccount(account);

        savedUser.setAccount(savedAccount);
        userRepository.save(savedUser);



        return savedUser;
    }

}
