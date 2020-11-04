package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/user/home")
    public ModelAndView home(ModelAndView modelAndView) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @GetMapping("/user/contact")
    public ModelAndView contact(ModelAndView modelAndView) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            modelAndView.setViewName("contact");
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}

