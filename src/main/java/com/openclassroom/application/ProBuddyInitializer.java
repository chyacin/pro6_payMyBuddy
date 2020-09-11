package com.openclassroom.application;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProBuddyInitializer implements CommandLineRunner {


 //   @Autowired
  //  UserService userService;

//    @Autowired
//    RoleService roleService;


    @Override
    public void run(String... args) throws Exception {
        ProBuddyRole proBuddyRoleUser = new ProBuddyRole("user");
        ProBuddyRole proBuddyRoleAdmin = new ProBuddyRole("admin");

//        roleService.createRole(proBuddyRoleUser);
//        roleService.createRole(proBuddyRoleAdmin);
    }

}
