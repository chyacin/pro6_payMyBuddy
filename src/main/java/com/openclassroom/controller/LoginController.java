package com.openclassroom.controller;

import com.openclassroom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@Controller
public class LoginController {

	@Autowired
	private RoleService roleService;

	@GetMapping(value = "/")
	@RolesAllowed("USER")
	public String getUser() {
		return "home";
	}

	@GetMapping(value = "/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin() {
		return "home";
	}

	// Login form
	@GetMapping("/login")
	public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
		return modelAndView;
	}
	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
