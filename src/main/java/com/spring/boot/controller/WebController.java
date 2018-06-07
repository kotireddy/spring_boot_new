package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManagerFactory;

@RestController
public class WebController {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@RequestMapping(value={"/","/home"})
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage() {
		System.out.println(entityManagerFactory.isOpen());
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
