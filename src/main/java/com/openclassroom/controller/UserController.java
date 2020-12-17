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


    /**
     * The controller method that routes to the registration page where new users can register
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and object we pass to the web page
     */
    @GetMapping("/register")
    public ModelAndView registerProBuddy(ModelAndView modelAndView){

        modelAndView.setViewName("register");
        modelAndView.addObject("user", new ProBuddyUserDTO());
        return modelAndView;
    }

    /**
     * The controller method which receives the registration form values and creates a user from it
     * @param user this is a data transfer object which contains the user's details
     * @param result the validation status of each input field in the form
     * @return String the next url to route to
     */
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

    /**
     * The controller method which routes to the page that can add a new connection to an existing user
     * @param user this is the logged in user details(information)
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     */
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

    /**
     * The controller which will process the added connection email and add it to the user's contact list
     * @param user this is the logged in user details(information)
     * @param connectedUserEmail is the String email of the user to be connected to
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @param redirectView an injected object which can perform a redirection to a url
     * @param result the validation status of each input field in the form
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     */
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

    /**
     * The controller method which returns the default error page
     * @return String that contains the url
     */
    @GetMapping("/403")
    public String error(){

        return "accessDenied";
    }
}
