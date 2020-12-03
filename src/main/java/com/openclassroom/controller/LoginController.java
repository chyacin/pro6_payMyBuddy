package com.openclassroom.controller;


import com.openclassroom.model.ProBuddyLogin;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.modelDTO.ProBuddyLoginDTO;
import com.openclassroom.service.LoginService;
import com.openclassroom.service.RoleService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




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

		System.out.println(user != null);
		user.getAuthorities().stream().forEach(a -> System.out.println(a.getAuthority()));

		if(user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
			return "redirect:/admin/loginHistory";
		}
		else{
			if(user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))){
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

	@GetMapping("/admin/loginHistory")
	@RolesAllowed("ADMIN")
	public ModelAndView loginHistory(@AuthenticationPrincipal ProBuddyUserDetails user,
									 ModelAndView modelAndView) {

		String loggedInName = user.getUsername(); //get logged in username

		ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);

		if (loggedInUser != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			List<ProBuddyLogin> loginList = loginService.findAllLogins();
			List<ProBuddyLoginDTO> proBuddyLoginList = new ArrayList<>();

			for (ProBuddyLogin proBuddyLogins : loginList) {
				ProBuddyLoginDTO logins = new ProBuddyLoginDTO();
				logins.setEmail(proBuddyLogins.getUser().getEmail());
				logins.setDate(proBuddyLogins.getDate());
				logins.setSuccess(proBuddyLogins.getSuccess());
				proBuddyLoginList.add(logins);
			}
			modelAndView.setViewName("loginHistory");
			modelAndView.addObject("loginList", new ProBuddyLogin());
			modelAndView.addObject("proBuddyLoginList", proBuddyLoginList);


		} else {
			if (loggedInUser != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
				modelAndView.setViewName("accessDenied");
			}

		}
		return modelAndView;
	}

	@GetMapping("/logout")
	public String signOut(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/login?logout";
	}



}
