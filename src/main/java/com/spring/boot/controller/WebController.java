package com.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class WebController {

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value={"/","/home"})
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage() {
		boolean dirty = entityManager.isOpen();
		System.out.println(dirty);
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
