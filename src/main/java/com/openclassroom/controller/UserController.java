package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private ProBuddyContacts proBuddyContacts;
    private ContactsService contactsService;

    @GetMapping("/register")
    public ModelAndView registerProBuddy(@ModelAttribute("user")ProBuddyUser proBuddyUser,
                                         ModelAndView modelAndView){

        modelAndView.setViewName("register");
        modelAndView.addObject("user");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewProBuddyUser(@Validated @ModelAttribute("user") ProBuddyUser proBuddyUser,
                                                ModelAndView modelAndView, BindingResult result){

        ProBuddyUser userByEmail = userService.findUserByEmail(proBuddyUser.getEmail());
        if(userByEmail.getEmail().contentEquals(proBuddyUser.getEmail())) {
            result.rejectValue("Please choose another email address","The email address that you entered is already taken");
        }
        if(result.hasErrors()) {
            modelAndView.setViewName("/register");
        }
        else{
            userService.createNewUserByRegistration(proBuddyUser);
            modelAndView.setViewName("/login");
        }
        return modelAndView;
    }



    @PostMapping("/user/addConnection")
    public ModelAndView createAddConnection(@ModelAttribute("user") String connectedUserEmail,
                                            ModelAndView modelAndView, RedirectView redirectView,
                                            BindingResult result){


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInName = user.getUsername();
        //Get the ProBuddyUser object of the person logged in

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);

        if(loggedInName.contentEquals(connectedUserEmail) == false){
            if(userService.findUserByEmail(connectedUserEmail) != null){
                contactsService.createContactsConnection(loggedInUser,connectedUserEmail);
                redirectView.setUrl("/user/transfer");
                modelAndView.setView(redirectView);
            }
            else {
                result.rejectValue("This email address doesn't exist", "Please enter a valid email address");
                modelAndView.setViewName("addConnection");
            }
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return  modelAndView;
    }
}
