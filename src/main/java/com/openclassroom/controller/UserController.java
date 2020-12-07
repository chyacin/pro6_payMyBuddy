package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyContacts;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.modelDTO.ProBuddyConnectedUserDTO;
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
        modelAndView.addObject("errorMessage", "");
        modelAndView.addObject("connectedUserEmail", new ProBuddyConnectedUserDTO());

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
                                                 @ModelAttribute("connectedUserEmail") ProBuddyConnectedUserDTO connectedUserEmail, ModelAndView modelAndView,
                                                 RedirectView redirectView, BindingResult result){

        if(result.hasErrors()) {
            modelAndView.setViewName("addUserConnection");
            return modelAndView;
        }
        String loggedInName = user.getUsername();

        //Get the ProBuddyUser object of the person logged in

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);

        if(loggedInName.contentEquals(connectedUserEmail.getConnectionEmail()) == false){
            ProBuddyUser userToConnectTo = userService.findUserByEmail(connectedUserEmail.getConnectionEmail());

            if(userToConnectTo != null){
                List<ProBuddyContacts> existingConnectedUser = contactsService.findConnectionWithThisUser(loggedInUser, userToConnectTo);
                System.out.println("Number of existing connection with " + connectedUserEmail + " " + existingConnectedUser.size());
                if(existingConnectedUser.size() == 0) {
                    contactsService.createContactsConnection(loggedInUser, connectedUserEmail.getConnectionEmail());

                   redirectView.setUrl("/user/makeTransfer");
                   modelAndView.setView(redirectView);
                }
                else{
                    System.out.println("Already connected with this user");
                    modelAndView.addObject("errorMessage", "Already connected with this user");

                    modelAndView.setViewName("addUserConnection");
                    return modelAndView;
                }

            }
            else {
                result.rejectValue(null, "Please enter a valid email address");
                modelAndView.addObject("errorMessage", "This email address doesn't exist");
                modelAndView.setViewName("addUserConnection");
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
