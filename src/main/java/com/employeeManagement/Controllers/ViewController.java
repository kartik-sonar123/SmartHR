package com.employeeManagement.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
//======================== STATIC PAGES ========================
	@GetMapping("/home")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}

	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}


}
