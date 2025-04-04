package com.employeeManagement.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EmpController {
	@Autowired
	EmpService empService;

	// Home Page
	@GetMapping("/home")
	public String name() {
		return "index";
	}

	// Dashboard - View All Employees
	@GetMapping("/employees")
	public String viewHomePage(ModelMap map) 
	{
		List<Employee> empList = empService.getAllEmployees();
		map.addAttribute("employees", empList);
		return "employees";
	}

	// Employee Management - Add Employee
	@GetMapping("/addEmp")
	public String viewEmpAddPage(ModelMap map) {
		map.addAttribute("employees", new Employee());
		return "addEmp";
	}

	@PostMapping("/save")
	public String saveEmp(@ModelAttribute Employee emp) {
		empService.saveEmp(emp);
		return "redirect:/employees";
	}

	// Employee Management - Edit Employee
	@GetMapping("/edit/{id}")
	public String editPage(@PathVariable int id, ModelMap map) {
		Optional<Employee> emp = empService.getEmployeeById(id);
		if (!emp.isPresent()) {
			return "redirect:/employees";
		}
		map.addAttribute("employee", emp.get());
		return "editEmp";
	}

	@PostMapping("/saveEdit/{id}")
	public String saveEdit(@PathVariable int id, @ModelAttribute Employee emp) {
		Optional<Employee> result = empService.getEmployeeById(id);
		if (result.isPresent()) {
			Employee existingEmployee = result.get();
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

	// Employee Management - Delete Employee
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable int id) {
		empService.deleteEmp(id);
		return "redirect:/employees";
	}

	// Static Pages
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	@GetMapping("/dashboard")
	public String employees() 
	{
		return "dashboard";
	}
	

	
}