package com.employeeManagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Employee;
import com.employeeManagement.repositories.EmpRepository;

@Service // This annotation indicates that this class is a service component in the Spring context
public class EmpService {

	@Autowired // Automatically injects the EmpRepository dependency into this service
	EmpRepository empRepository;

	// Method to get all employees with their associated user data
	public List<Employee> getAllEmployeesWithUser() {
		return empRepository.findAllEmployeesWithUser(); // Calls the repository method to get all employees with user data
	}

	// Method to save or update an employee
	public void saveEmp(Employee emp) {
		empRepository.save(emp); // Uses the repository's save method to persist the employee
	}

	// Method to delete an employee by their ID
	public void deleteEmp(int id) {
		empRepository.deleteById(id); // Calls the deleteById method of the repository to delete an employee by ID
	}

	// Method to get an employee by their ID
	public Optional<Employee> getEmployeeById(int id) {
		return empRepository.findById(id); // Uses the findById method of the repository to find the employee by ID
	}

	// Method to get an employee by their associated user's email
	public Employee getEmployeeByEmail(String email) {
		return empRepository.findByUserEmail(email); // Calls the repository method to find the employee by the user's email
	}

	// Method to get the total number of employees
	public long getTotalEmployees() {
		return empRepository.countTotalEmployees(); // Calls the repository method to count the total number of employees
	}

	// Method to get all employees without user data
	public List<Employee> getAllEmployees() {
		return empRepository.findAll(); // Uses the findAll method to fetch all employees
	}
}
