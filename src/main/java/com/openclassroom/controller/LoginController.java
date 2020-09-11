package com.openclassroom.controller;

import com.openclassroom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class LoginController {

	@Autowired
	private RoleService roleService;



	@GetMapping(value = "/")
	@RolesAllowed("USER")
	public String getUser() {

		return "landingPage";
	}

	@GetMapping(value = "/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin() {
		return "landingPage";
	}

	// Login form
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		System.out.println("In login error page");
		model.addAttribute("loginError", true);
		return "login";
	}

}
