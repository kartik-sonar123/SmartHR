package com.employeeManagement.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	// ======================== STATIC PAGES ========================

	// Endpoint for the Home page
	@GetMapping("/home")
	public String viewHomePage() {
		// Returns the view name "index" which corresponds to the index page
		return "index";
	}

	// Endpoint for the Contact page
	@GetMapping("/contact")
	public String contactPage() {
		// Returns the view name "contact" which corresponds to the contact page
		return "contact";
	}

	// Endpoint for the About page
	@GetMapping("/about")
	public String aboutPage() {
		// Returns the view name "about" which corresponds to the about page
		return "about";
	}
}
