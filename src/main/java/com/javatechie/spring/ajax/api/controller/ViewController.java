package com.javatechie.spring.ajax.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/search")
	public String searchPass() {
		return "search";
	}
	
	@GetMapping("/testLayUI")
	public String testLayUI() {
		return "testLayUI";
	}
}
