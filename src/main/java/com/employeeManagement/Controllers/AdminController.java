package com.employeeManagement.Controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	EmpService empService;

	//******************** Authorization Controller *******************
	// ======================== AdminDASHBOARD ========================
	@GetMapping("/adminDashboard")
	public String viewAdminDashboardPage(HttpSession session) {
	    User user = (User) session.getAttribute("loggedInUser");

	    if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
	        return "redirect:/login"; // or a 403 error page
	    }

	    return "adminDashboard";
	}
  //*********************************************************************

	// ======================== EMPLOYEE MANAGEMENT ========================
	// View All Employees
	@GetMapping("/employees")
	public String viewEmployeePage(ModelMap model) {
		List<Employee> empList = empService.getAllEmployees();
		model.addAttribute("employees", empList);
		return "employees";
	}

	// Show Add Employee Page
	@GetMapping("/addEmp")
	public String viewEmpAddPage(ModelMap model) {
		model.addAttribute("employees", new Employee());
		return "addEmp";
	}

	// Handle Add Employee Form Submission
	@PostMapping("/save")
	public String saveEmp(@ModelAttribute Employee emp) {
		empService.saveEmp(emp);
		return "redirect:/employees";
	}

	// Show Edit Employee Page
	@GetMapping("/edit/{id}")
	public String editPage(@PathVariable int id, ModelMap model) {
		Optional<Employee> emp = empService.getEmployeeById(id);
		if (!emp.isPresent()) {
			return "redirect:/employees";
		}
		model.addAttribute("employee", emp.get());
		return "editEmp";
	}

	// Handle Edit Employee Form Submission
	@PostMapping("/saveEdit/{id}")
	public String saveEdit(@PathVariable int id, @ModelAttribute Employee emp) {
		Optional<Employee> result = empService.getEmployeeById(id);
		if (result.isPresent()) {
			Employee existingEmployee = result.get();
			// Update the existing employee with new values
			existingEmployee.setName(emp.getName());
			existingEmployee.setEmail(emp.getEmail());
			existingEmployee.setPhoneNo(emp.getPhoneNo());
			existingEmployee.setJobRole(emp.getJobRole());
			existingEmployee.setSalary(emp.getSalary());
			existingEmployee.setAddress(emp.getAddress());

			empService.saveEmp(existingEmployee);
		}
		return "redirect:/employees";
	}

	// Delete Employee
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable int id) {
		empService.deleteEmp(id);
		return "redirect:/employees";
	}

}
