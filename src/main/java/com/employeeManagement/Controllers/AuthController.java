package com.employeeManagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	// Show the registration page
	@GetMapping("/register")
	public String viewRegistrationPage(ModelMap model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// Handle registration form submission
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "login"; // After registration, go to login page
	}

	// ======================== LOGIN & LOGOUT ========================

	// Show the login page
	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}

	// Handle login form submission
	@PostMapping("/profile")
	public String profile(@RequestParam String email,
	                      @RequestParam String password,
	                      HttpSession session,
	                      ModelMap model,
	                      RedirectAttributes redirectAttributes) {

		User user = userService.getUserByEmail(email);

		// Check if user exists and password is correct
		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("loggedInUser", user);

			// Redirect to admin or employee dashboard based on role
			if (user.getRole().equalsIgnoreCase("admin")) {
				return "redirect:/adminDashboard";
			} else {
				return "redirect:/employee-profile";
			}
		}

		// If login fails
		redirectAttributes.addFlashAttribute("error", "Incorrect email or password");
		return "redirect:/login";
	}

	// Logout the user and invalidate session
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // Clear user session
		return "redirect:/login"; // Return to login page
	}

	// ======================== EMPLOYEE PROFILE (For Employee Role) ========================

	// Show profile page for the logged-in employee
	@GetMapping("/employee-profile")
	public String employeeProfile(HttpSession session,
	                              ModelMap model,
	                              RedirectAttributes redirectAttributes) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");

		// If user is not logged in
		if (loggedInUser == null) {
			return "redirect:/login";
		}

		// Get employee record linked with logged-in user's email
		Employee employee = empService.getEmployeeByEmail(loggedInUser.getEmail());

		// If admin hasn't added this employee yet
		if (employee == null) {
			redirectAttributes.addFlashAttribute("loginError",
					"You haven't been added to the system by the admin yet. Please wait for approval.");
			return "redirect:/login";
		}

		model.addAttribute("employee", employee);
		return "employee-profile";
	}
}
