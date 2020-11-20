package com.openclassroom.controller;


import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyLogin;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.service.LoginService;
import com.openclassroom.service.UserService;
import com.openclassroom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

	private ProBuddyUser proBuddyUser;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	@GetMapping(value = "/")
	public String getUser(@AuthenticationPrincipal ProBuddyUserDetails user) {

		String loggedInName = user.getUsername(); //get logged in username

		ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
		if (loggedInUser != null && loggedInUser.equals("ADMIN"))  {
			return "loginHistory";
		}
		else{
			if(loggedInUser != null && loggedInUser.equals("USER") ){
				return "home";
			}
		}
		return "login";
	}

	@GetMapping(value = "/admin")
	@RolesAllowed("ADMIN")
	public ModelAndView getAdmin(ModelAndView modelAndView) {

		modelAndView.setViewName("loginHistory");
		return modelAndView;
	}

	// Login form
	@GetMapping("/login")
	public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping("/admin/login")
	@RolesAllowed("ADMIN")
	public ModelAndView loginHistory(@AuthenticationPrincipal ProBuddyUserDetails user,
									 ModelAndView modelAndView){

		String loggedInName = user.getUsername(); //get logged in username

		ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
		if (loggedInUser != null) {
			List<ProBuddyUser> userList = userService.findAllUsersByEmail(user.getUsername());
			List<ProBuddyLogin> loginList = loginService.findAll();
			List<ProBuddyLogin>  proBuddyLoginList = new ArrayList<>();

			for (ProBuddyLogin proBuddyLogins : loginList){
				 ProBuddyLogin logins = new ProBuddyLogin();
				 logins.setUser(proBuddyLogins.getUser());
				 logins.setDate(proBuddyLogins.getDate());
				 logins.setSuccess(proBuddyLogins.getSuccess());
				 proBuddyLoginList.add(logins);

				 modelAndView.setViewName("loginHistory");
				 modelAndView.addObject("userList", userList);
				 modelAndView.addObject("loginList", new ProBuddyLogin());
				 modelAndView.addObject("proBuddyLoginList", proBuddyLoginList);

			}

		}
		return modelAndView;
	}

}
