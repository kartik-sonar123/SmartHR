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

	/**
	 * GET method for leave application form URL: /leave Shows form for applying a
	 * new leave
	 */
	@GetMapping("/leave")
	public String leaveFormPage(ModelMap model, HttpSession session) {
		// Add a new LeaveRequest object to bind form data
		model.addAttribute("leave", new LeaveRequest());

		// Check if user is logged in
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			return "employee-leave"; // Thymeleaf view to show the leave form
		} else {
			return "redirect:/login"; // Redirect to login page if session expired
		}
	}

	/**
	 * POST method for submitting leave application URL: /apply Accepts form data
	 * and saves it for the logged-in employee
	 */
	@PostMapping("/apply")
	public String leaveApply(@ModelAttribute LeaveRequest leaveRequest, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			// Get employee using the logged-in user's email
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Attach employee to the leave request
			leaveRequest.setEmployee(employee);

			// Save the leave request
			leaveService.applyLeave(leaveRequest);
		}

		return "redirect:/leave"; // Redirect back to the form after submission
	}

	/**
	 * GET method to show leave history for the logged-in employee URL:
	 * /leave-history
	 */
	@GetMapping("/leave-history")
	public String employeeLeaveHistory(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			// Get employee details
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Fetch all leaves of that employee
			List<LeaveRequest> leaves = leaveService.getLeavesByEmployee(employee);

			// Add data to model to render in Thymeleaf
			model.addAttribute("leaves", leaves);

			return "employeeLeaveHistroy"; // Make sure this matches your HTML file
		} else {
			return "redirect:/login"; // Redirect to login if session not found
		}
	}	
}
