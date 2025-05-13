package com.employeeManagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.LeaveRequest;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.LeaveService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeLeaveController {

	@Autowired
	LeaveService leaveService;

	@Autowired
	EmpService empService;

	// ======================== LEAVE APPLICATION FORM ========================

	/**
	 * Display the leave application form
	 * URL: /leave
	 * This method shows the form for applying for a new leave.
	 */
	@GetMapping("/leave")
	public String leaveFormPage(ModelMap model, HttpSession session) {
		// Add an empty LeaveRequest object to bind form data
		model.addAttribute("leave", new LeaveRequest());

		// Check if the user is logged in
		User user = (User) session.getAttribute("loggedInUser");

		// If logged in, return the leave form page, otherwise redirect to login
		if (user != null) {
			return "employee-leave"; // Thymeleaf view for leave form
		} else {
			return "redirect:/login"; // Redirect to login page if not logged in
		}
	}

	// ======================== APPLY LEAVE ========================

	/**
	 * Handle the submission of the leave application form
	 * URL: /apply
	 * This method accepts the leave request form data and saves it for the logged-in employee.
	 */
	@PostMapping("/apply")
	public String leaveApply(@ModelAttribute LeaveRequest leaveRequest, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		// Check if the user is logged in
		if (user != null) {
			// Get employee details using the logged-in user's email
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Attach the employee to the leave request
			leaveRequest.setEmployee(employee);

			// Save the leave request to the database
			leaveService.applyLeave(leaveRequest);
		}

		// After submission, redirect back to the leave application form
		return "redirect:/leave";
	}

	// ======================== LEAVE HISTORY ========================

	/**
	 * Show the leave history for the logged-in employee
	 * URL: /leave-history
	 * This method fetches and displays all leave requests for the logged-in employee.
	 */
	@GetMapping("/leave-history")
	public String employeeLeaveHistory(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		// Check if the user is logged in
		if (user != null) {
			// Fetch employee details using the logged-in user's email
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Get the list of all leaves for this employee
			List<LeaveRequest> leaves = leaveService.getLeavesByEmployee(employee);

			// Add the leave data to the model for rendering in Thymeleaf
			model.addAttribute("leaves", leaves);

			// Return the leave history page
			return "employeeLeaveHistroy"; // Ensure this matches the HTML file
		} else {
			return "redirect:/login"; // Redirect to login if not logged in
		}
	}	
}
