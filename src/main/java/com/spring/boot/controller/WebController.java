package com.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {



	@RequestMapping(value={"/","/home"})
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/user")
	public ModelAndView userPage() {
		System.out.println("User");
		return new ModelAndView("user");
	}
	
	@RequestMapping(value="/admin")
	public ModelAndView adminPage() {
		System.out.println("Admin");
		return new ModelAndView("admin");
	}
	
}
