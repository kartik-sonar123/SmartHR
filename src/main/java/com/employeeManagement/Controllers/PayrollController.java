package com.employeeManagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.Payroll;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.PayrollService;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller 
public class PayrollController {

	// Autowiring the PayrollService to interact with payroll data
	@Autowired
	private PayrollService payrollService;

	// Autowiring EmpService to fetch employee data
	@Autowired
	EmpService empService;
	@Autowired
	private EmpService employeeService;

	/**
	 * This method returns the generate payroll page for a specific employee.
	 * It takes the employee ID as a path variable and shows the payroll generation form.
	 */
	@GetMapping("/generate-payroll/{id}")
	public String generatePayrollPage(@PathVariable("id") int employeeId, ModelMap model) {
		// Fetching the employee details based on the employeeId
		Optional<Employee> optionalEmployee = employeeService.getEmployeeById(employeeId);

		// If the employee is found, add employee data to the model and return the payroll form view
		if (optionalEmployee.isPresent()) {
			model.addAttribute("employee", optionalEmployee.get());
			return "generate-payroll"; // Show the payroll generation form
		} else {
			// Redirect to error page if the employee is not found
			return "redirect:/error"; // or a custom error page like "employee-not-found"
		}
	}

	/**
	 * This method processes the payroll generation for a specific employee
	 * based on the provided month.
	 */
	@PostMapping("/payroll/generate/{id}")
	public String generatePayroll(@PathVariable("id") int employeeId, @RequestParam String month) {
		// Generate payroll for the employee and specified month
		payrollService.generatePayroll(employeeId, month);
		// After generating payroll, redirect to the payroll history page for the employee
		return "redirect:/payroll/employee/{id}";
	}

	/**
	 * This method retrieves the payroll history of a specific employee.
	 * It takes the employee ID as a path variable and displays all payroll records.
	 */
	@GetMapping("/payroll/employee/{id}")
	public String getPayrollHistory(@PathVariable("id") int employeeId, ModelMap model) {
		// Fetching the payroll history for the given employee ID
		List<Payroll> payrolls = payrollService.getPayrollsByEmployeeId(employeeId);
		// Adding the payroll data to the model to render it in the view
		model.addAttribute("payrolls", payrolls);
		// Returning the view that shows the payroll history
		return "payroll-history";
	}

	/**
	 * This method displays the salary history for the logged-in employee.
	 * It fetches the logged-in user's employee details and displays their payroll history.
	 */
	@GetMapping("/salary")
	public String viewSalaryHistory(HttpSession session, ModelMap model, RedirectAttributes redirectAttributes) {
		// Retrieve the logged-in user from the session
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		// If no user is logged in, redirect to the login page
		if (loggedInUser == null) {
			return "redirect:/login";
		}

		// Fetch the employee details using the logged-in user's email
		Employee employee = empService.getEmployeeByEmail(loggedInUser.getEmail());

		// If employee is not found, notify the user and redirect to the login page
		if (employee == null) {
			redirectAttributes.addFlashAttribute("error", "You are not added by admin yet.");
			return "redirect:/login";
		}

		// Fetching the salary history (payroll history) of the employee
        List<Payroll> salaryHistory = payrollService.getPayrollsByEmployeeId(employee.getId());
		// Adding the salary history to the model to be displayed in the view
		model.addAttribute("salaryHistory", salaryHistory);

		// Returning the view that displays the salary history
		return "salary"; // Thymeleaf view for displaying the salary history
	}
}
