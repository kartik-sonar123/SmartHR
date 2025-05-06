package com.employeeManagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Employee;
import com.employeeManagement.repositories.EmpRepository;

@Service
public class EmpService {

	@Autowired
	EmpRepository empRepository;

	// Get all employees along with their associated user data
	public List<Employee> getAllEmployeesWithUser() {
		return empRepository.findAllEmployeesWithUser();
	}

	// Save or update an employee
	public void saveEmp(Employee emp) {
		empRepository.save(emp);
	}

	// Delete an employee by ID
	public void deleteEmp(int id) {
		empRepository.deleteById(id);
	}

	// Get employee by ID
	public Optional<Employee> getEmployeeById(int id) {
		return empRepository.findById(id);
	}

	// Get employee by user email
	public Employee getEmployeeByEmail(String email) {
		return empRepository.findByUserEmail(email);
	}

	// Get total number of employees
	public long getTotalEmployees() {
		return empRepository.countTotalEmployees();
	}
}
