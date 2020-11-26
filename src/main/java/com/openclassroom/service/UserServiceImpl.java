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
    public List<ProBuddyUser> findAllUsersByEmail(String email) {
      List<ProBuddyUser> userList = userRepository.findAll().stream().filter(pro -> pro.getEmail().equals(email))
              .collect(Collectors.toList());
      return userList;
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

   /* private double generateRandomAccountBalance(){
        return Math.round(Math.random() * 10000);
        //String initialAmount = decimalFormat.format(Math.random() * 10000);
        //return Double.parseDouble(initialAmount);
    }*/


}
