
package com.employeeManagement.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.UserRepository;
import com.employeeManagement.services.AttendanceService;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.LeaveService;
import com.employeeManagement.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	// Autowiring services to interact with employee, user, attendance, and leave
	// data
	@Autowired
	EmpService empService;

	@Autowired
	UserService userService;

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	LeaveService leaveService;

	@Autowired
	UserRepository userRepository;

	// ******************** Authorization Controller *******************
	// ======================== Admin Dashboard ========================

	// Show Admin Dashboard with key statistics: Total employees, present employees,
	// and pending leave requests
	@GetMapping("/adminDashboard")
	public String viewAdminDashboardPage(HttpSession session, ModelMap model) {
		// Get the currently logged-in user from session
		User user = (User) session.getAttribute("loggedInUser");

		// Check if no user is logged in or if user is not an admin
		if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
			return "redirect:/login"; // Redirect to login if not authorized
		}

		// Fetch total number of employees
		long totalEmployees = empService.getTotalEmployees();
		model.addAttribute("totalEmployees", totalEmployees);

		// Fetch today's present employee count
		long presentEmployees = attendanceService.getTodayPresentEmployees();
		model.addAttribute("presentEmployees", presentEmployees);

		// Fetch count of pending leave requests
		long pendingLeaveCount = leaveService.getPendingLeaveCount();
		model.addAttribute("pendingLeaveCount", pendingLeaveCount);

		// Return the admin dashboard view
		return "adminDashboard";
	}

	// *********************************************************************

	// ======================== Employee Management ========================

	// View All Employees with associated user information
	@GetMapping("/employees")
	public String viewEmployeePage(ModelMap model) {
		List<Employee> empList = empService.getAllEmployeesWithUser();
		model.addAttribute("employees", empList);
		return "employees"; // Return the employees list view
	}

	// Show Add Employee Page
	@GetMapping("/addEmp")
	public String viewEmpAddPage(ModelMap model) {
		model.addAttribute("employees", new Employee()); // Empty employee object for the form
		model.addAttribute("users", userRepository.findAll()); // Send user list for selection
		return "addEmp"; // Return the add employee page view
	}

	// Handle Add Employee Form Submission
	@PostMapping("/save")
	public String saveEmp(@ModelAttribute Employee emp, @RequestParam("userId") int userId) {
		// Fetch user by userId and associate with employee
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			emp.setUser(user); // Set the user to the employee object
		}
		// Save the employee to the database
		empService.saveEmp(emp);
		return "redirect:/employees"; // Redirect to the employees list page
	}

	// Show Edit Employee Page
	@GetMapping("/editEmp/{id}")
	public String editPage(@PathVariable int id, ModelMap model) {
		Optional<Employee> emp = empService.getEmployeeById(id);
		if (!emp.isPresent()) {
			return "redirect:/employees";
		}
		model.addAttribute("employee", emp.get());
		return "editEmp";
	}

	// Handle Edit Employee Form Submission
	// Handle Edit Employee Form Submission
	@PostMapping("/saveEdit/{id}")
	public String saveEdit(@PathVariable("id") int id, @ModelAttribute Employee emp) {
		Optional<Employee> result = empService.getEmployeeById(id);
		if (result.isPresent()) {
			Employee existingEmployee = result.get();
			// Update fields
			existingEmployee.setName(emp.getName());
			existingEmployee.setJobRole(emp.getJobRole());
			existingEmployee.setDepartment(emp.getDepartment());
			existingEmployee.setSalary(emp.getSalary());
			empService.saveEmp(existingEmployee);
		}
		return "redirect:/employees";
	}

	// ======================== Employee Deletion =========================

	// Delete Employee and associated User by Employee ID
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable int id) {
		Optional<Employee> emp = empService.getEmployeeById(id); // Fetch employee by ID
		if (emp.isPresent()) {
			Employee employee = emp.get(); // Get associated employee
			empService.deleteEmp(id); // Delete employee first
			userService.deleteUser(employee.getUser().getId()); // Then delete the associated user
		}
		return "redirect:/employees"; // Redirect to the employees list page after deletion
	}

	// ======================== Employee Count ============================

	// Get the total number of employees in the system
	@GetMapping("/total-employees")
	public ResponseEntity<Long> getTotalEmployees() {
		return ResponseEntity.ok(empService.getTotalEmployees()); // Return the total employee count
	}

	// Get the total number of employees present today
	@GetMapping("/present-employees")
	public ResponseEntity<Long> getPresentEmployees() {
		return ResponseEntity.ok(attendanceService.getTodayPresentEmployees()); // Return the count of present employees
	}

}
