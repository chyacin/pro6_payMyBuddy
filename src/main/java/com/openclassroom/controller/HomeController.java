package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    private ProBuddyUser proBuddyUser;


    /**
     * The controller method that routes the user to the home page
     * @param user this is the logged in user details(information)
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and object we pass to the web page.
     */
    @GetMapping("/user/home")
    public ModelAndView home(@AuthenticationPrincipal ProBuddyUserDetails user, ModelAndView modelAndView) {
        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            modelAndView.setViewName("home");
            modelAndView.addObject("user", loggedInUser);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    /**
     * The controller method that routes the user to the contact page
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes
     * @return ModelAndView which contains the stored attributes and object we pass to the web page.
     */
    @GetMapping("/user/contact")
    public ModelAndView contact( ModelAndView modelAndView) {
        modelAndView.setViewName("contact");
        return modelAndView;
    }
}

