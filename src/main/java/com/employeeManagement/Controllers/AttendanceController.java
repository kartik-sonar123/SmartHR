package com.employeeManagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.employeeManagement.models.Attendance;
import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.services.AttendanceService;
import com.employeeManagement.services.EmpService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private EmpService empService;

	// Show attendance form to logged-in employee
	@GetMapping("/attendance")
	public String showAttendancePage(ModelMap model, HttpSession session) {
		// Get logged in user from session
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			// Add user and a new Attendance object to the model for form binding
			model.addAttribute("user", user);
			model.addAttribute("attendance", new Attendance());
			return "attendance"; // Return attendance form view
		} else {
			// Redirect to login if user not logged in
			return "redirect:/login";
		}
	}

	// Handle attendance form submission by employee
	@PostMapping("/markAttendance")
	public String markAttendance(@ModelAttribute Attendance attendance, HttpSession session, ModelMap model,
			RedirectAttributes redirectAttributes) {

		// Get logged in user from session
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			// Fetch employee details using user's email
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Set employee for attendance record
			attendance.setEmployee(employee);

			// Try to mark attendance using service
			boolean success = attendanceService.markAttendance(attendance);

			if (!success) {
				// If attendance already marked for today, show error on the attendance page
				model.addAttribute("user", user);
				model.addAttribute("error", "Attendance already marked for today.");
				return "attendance";
			}

			// On success, add flash attribute for success message and redirect to
			// attendance page
			redirectAttributes.addFlashAttribute("success", "Attendance marked successfully.");
			return "redirect:/attendance";
		}

		// If user not logged in, redirect to attendance page (can be changed to
		// redirect to login)
		return "redirect:/attendance";
	}

	// Admin view to display all attendance records with employee info
	@GetMapping("/admin/attendance")
	public String viewAllAttendance(ModelMap model) {
		// Get all attendance records from service
		List<Attendance> attendanceList = attendanceService.getAllAttendances();

		// Add list to model for display on admin-attendance page
		model.addAttribute("attendanceList", attendanceList);
		return "admin-attendance";
	}

	// Show logged-in employee's attendance history
	@GetMapping("/empAttendanceHistory")
	public String employeeAttendanceHistory(ModelMap model, HttpSession session) {
		// Get logged in user from session
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			// Fetch employee details using user's email
			Employee employee = empService.getEmployeeByEmail(user.getEmail());

			// Get attendance records for the employee
			List<Attendance> attendanceList = attendanceService.getAttendanceByEmployee(employee);

			// Add attendance list to model for display on employee attendance history page
			model.addAttribute("attendanceList", attendanceList);
			return "empAttendanceHistory";
		} else {
			// Redirect to login if user not logged in
			return "redirect:/login";
		}
	}

}
