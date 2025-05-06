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

	/**
	 * Show the attendance form to the employee URL: /attendance
	 */
	@GetMapping("/attendance")
	public String showAttendancePage(ModelMap model, HttpSession session) {

		// Check if user is logged in
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			model.addAttribute("user", user);
			model.addAttribute("attendance", new Attendance());
			return "attendance"; // Thymeleaf view (attendance.html)
		} else {
			return "redirect:/login"; // If not logged in, redirect to login
		}
	}

	/**
	 * Handles the form submission to mark attendance URL: /markAttendance
	 */
	@PostMapping("/markAttendance")
	public String markAttendance(@ModelAttribute Attendance attendance, HttpSession session, ModelMap model,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {
			Employee employee = empService.getEmployeeByEmail(user.getEmail());
			attendance.setEmployee(employee);

			boolean success = attendanceService.markAttendance(attendance);

			if (!success) {
				model.addAttribute("user", user);
				model.addAttribute("error", "Attendance already marked for today.");
				return "attendance"; // Show error on same page
			}

			redirectAttributes.addFlashAttribute("success", "Attendance marked successfully.");
			return "redirect:/attendance"; // Redirect with success message
		}

		return "redirect:/attendance";
	}

	@GetMapping("/admin/attendance")
	public String viewAllAttendance(ModelMap model) {
		List<Attendance> attendanceList = attendanceService.getAllAttendances();
		model.addAttribute("attendanceList", attendanceList);
		return "admin-attendance"; // View for admin
	}
}
