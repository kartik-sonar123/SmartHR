package com.employeeManagement.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	EmpService empService;

	// ======================== REGISTRATION ========================

	// Show Registration Page
	@GetMapping("/register")
	public String viewRegistrationPage(ModelMap model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// Handle Registration Form Submission
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "login"; // Redirect to login page after successful registration
	}

	// ======================== LOGIN & LOGOUT ========================

	// Show Login Page
	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}

	// Handle Login Form Submission
	@PostMapping("/profile")
	public String profile(@RequestParam String email, @RequestParam String password, HttpSession session,
			ModelMap model) {
		User user = userService.getUserByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("loggedInUser", user);

			// Redirect based on role
			if (user.getRole().equalsIgnoreCase("admin")) {
				return "redirect:/adminDashboard";
			} else {
				return "redirect:/employee-profile";
			}
		}

		// If login fails
		model.addAttribute("error", "Incorrect email or password");
		return "login";
	}

	// Logout User
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // Clear session
		return "redirect:/login"; // Redirect to login page
	}

	
	 //*********************** Authentication Controller ***********************
	//======================== EMPLOYEE PROFILE (FOR USER ROLE) ========================

	// Show Employee Profile Page for Logged-in User
	@GetMapping("/employee-profile")
	public String employeeProfile(HttpSession session, ModelMap model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login"; // If not logged in, redirect to login
		}

		// Fetch employee details based on user's email
		Employee employee = empService.getEmployeeByEmail(loggedInUser.getEmail());
		model.addAttribute("employee", employee);
		return "employee-profile";
	}
}
