package com.employeeManagement.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.EmpRepository;
import com.employeeManagement.repositories.UserRepository;
import com.employeeManagement.services.AttendanceService;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.LeaveService;
import com.employeeManagement.services.PayrollService;
import com.employeeManagement.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	// ================= Dependency Injection =================
	@Autowired
	EmpService empService;

	@Autowired
	PayrollService payrollService;

	@Autowired
	UserService userService;

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	LeaveService leaveService;

	@Autowired
	EmpRepository empRepository;

	@Autowired
	UserRepository userRepository;

	// =================== Admin Dashboard ====================

	/**
	 * Displays the admin dashboard with summary data.
	 */
	@GetMapping("/adminDashboard")
	public String viewAdminDashboardPage(HttpSession session, ModelMap model) {
		User user = (User) session.getAttribute("loggedInUser");

		// Redirect if not logged in or not an admin
		if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
			return "redirect:/login";
		}

		// Fetch and add total employees
		long totalEmployees = empService.getTotalEmployees();
		model.addAttribute("totalEmployees", totalEmployees);

		// Fetch and add present employees today
		long presentEmployees = attendanceService.getTodayPresentEmployees();
		model.addAttribute("presentEmployees", presentEmployees);

		// Fetch and add pending leave requests
		long pendingLeaveCount = leaveService.getPendingLeaveCount();
		model.addAttribute("pendingLeaveCount", pendingLeaveCount);

		// Fetch payroll summary data
		double totalNetSalary = payrollService.getTotalNetSalary();
		int processedEmployees = payrollService.getProcessedEmployeeCount();
		int totalEmployees1 = empService.getAllEmployees().size();

		// Add payroll data to model
		model.addAttribute("totalNetSalary", totalNetSalary);
		model.addAttribute("processedEmployees", processedEmployees);
		model.addAttribute("totalEmployees", totalEmployees1);

		return "adminDashboard";
	}

	// ============== Employee Management Pages ================

	/**
	 * View all employees along with their associated users.
	 */
	@GetMapping("/employees")
	public String viewEmployeePage(ModelMap model) {
		List<Employee> empList = empService.getAllEmployeesWithUser();
		model.addAttribute("employees", empList);
		return "employees";
	}

	/**
	 * Show the form for adding a new employee.
	 */
	@GetMapping("/addEmp")
	public String viewEmpAddPage(ModelMap model) {
		model.addAttribute("employees", new Employee()); // Empty form object
		model.addAttribute("users", userRepository.findAll()); // User list for dropdown
		return "addEmp";
	}

	/**
	 * Handle form submission for saving a new employee.
	 */
	@PostMapping("/save")
	public String saveEmp(@ModelAttribute Employee emp) {
		User user = userRepository.findById(emp.getUser().getId()).orElse(null);
		if (user != null) {
			emp.setUser(user);
		}
		empService.saveEmp(emp);
		return "redirect:/employees";
	}

	/**
	 * Show the form for editing an existing employee.
	 */
	@GetMapping("/editEmp/{id}")
	public String editPage(@PathVariable int id, ModelMap model) {
		Optional<Employee> emp = empService.getEmployeeById(id);
		if (!emp.isPresent()) {
			return "redirect:/employees";
		}
		model.addAttribute("employee", emp.get());
		return "editEmp";
	}

	/**
	 * Handle form submission for updating an employee's details.
	 */
	@PostMapping("/saveEdit/{id}")
	public String saveEdit(@PathVariable("id") int id, @ModelAttribute Employee emp) {
		Optional<Employee> result = empService.getEmployeeById(id);
		if (result.isPresent()) {
			Employee existingEmployee = result.get();
			existingEmployee.setName(emp.getName());
			existingEmployee.setJobRole(emp.getJobRole());
			existingEmployee.setDepartment(emp.getDepartment());
			existingEmployee.setSalary(emp.getSalary());
			empService.saveEmp(existingEmployee);
		}
		return "redirect:/employees";
	}

	// ============== Employee Deletion ================

	/**
	 * Delete an employee and their associated user by ID.
	 */
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable int id) {
		Optional<Employee> emp = empService.getEmployeeById(id);
		if (emp.isPresent()) {
			Employee employee = emp.get();
			empService.deleteEmp(id);
			userService.deleteUser(employee.getUser().getId());
		}
		return "redirect:/employees";
	}

	// ============== REST APIs for Statistics ================

	/**
	 * Get total employee count.
	 */
	@GetMapping("/total-employees")
	public ResponseEntity<Long> getTotalEmployees() {
		return ResponseEntity.ok(empService.getTotalEmployees());
	}

	/**
	 * Get present employees count for today.
	 */
	@GetMapping("/present-employees")
	public ResponseEntity<Long> getPresentEmployees() {
		return ResponseEntity.ok(attendanceService.getTodayPresentEmployees());
	}

	// ============== Payroll Pages ================

	/**
	 * Show the payroll employee list page.
	 */
	@GetMapping("/payroll")
	public String showPayrollPage(ModelMap model) {
		List<Employee> employees = empService.getAllEmployees();
		model.addAttribute("employees", employees);
		return "payroll-employees";
	}

	/**
	 * Show the admin dashboard with monthly payroll summary.
	 */
	@GetMapping("/admin/dashboard")
	public String showAdminDashboard(ModelMap model) {
		Map<String, Object> summary = payrollService.getMonthlyPayrollSummary();
		model.addAttribute("totalNetSalary", summary.get("totalNetSalary"));
		model.addAttribute("processedEmployees", summary.get("processedEmployees"));
		model.addAttribute("totalEmployees", summary.get("totalEmployees"));
		return "admin-dashboard";
	}
}
