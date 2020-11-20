package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.modelDTO.ProBuddyUserDTO;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private ProBuddyAccount proBuddyAccount;
    private ProBuddyContacts proBuddyContacts;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactsService contactsService;



    @GetMapping("/register")
    public ModelAndView registerProBuddy(ModelAndView modelAndView){

        modelAndView.setViewName("register");
        modelAndView.addObject("user", new ProBuddyUserDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerNewProBuddyUser(@ModelAttribute("user") @Valid ProBuddyUserDTO user,
                                              BindingResult result){

        ProBuddyUser userByEmail = userService.findUserByEmail(user.getEmail());
        if(userByEmail != null) {
            result.rejectValue("email",null, "The email address that you entered is already taken");
        }
        if(result.hasErrors()) {
            return "register";
        }
        else{
            userService.createNewUserByRegistration(user);
            return "login";
        }
    }

    @GetMapping("/user/addUserConnection")
    public ModelAndView addUserConnection(@AuthenticationPrincipal ProBuddyUserDetails user, ModelAndView modelAndView){

        String loggedInName = user.getUsername(); //get logged in username


        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);

        if(loggedInName != null){
            modelAndView.setViewName("addUserConnection");
            modelAndView.addObject("user", loggedInUser);

        }
        else{
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/addUserConnection")
    public ModelAndView createAddUserConnection (@AuthenticationPrincipal  ProBuddyUserDetails user,
                                                 @RequestParam String connectedUserEmail, ModelAndView modelAndView,
                                                 RedirectView redirectView, BindingResult result){

        String loggedInName = user.getUsername();

        //Get the ProBuddyUser object of the person logged in

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);

        if(loggedInName.contentEquals(connectedUserEmail) == false){
            ProBuddyUser userToConnectTo = userService.findUserByEmail(connectedUserEmail);
            if(userToConnectTo != null){
                List<ProBuddyContacts> existingConnectedUser = contactsService.findConnectionWithThisUser(loggedInUser, userToConnectTo);
                System.out.println("Number of existing connection with " + connectedUserEmail + " " + existingConnectedUser.size());
                if(existingConnectedUser.size() == 0) {
                    contactsService.createContactsConnection(loggedInUser, connectedUserEmail);
                }
                redirectView.setUrl("/user/makeTransfer");
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

    @GetMapping("/403")
    public String error(){

        return "accessDenied";
    }
}
