package com.employeeManagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.EmpRepository;
import com.employeeManagement.repositories.UserRepository;

@Service
public class EmpService {

	@Autowired
	EmpRepository empRepository;
	public List<Employee> getAllEmployees()
	{
		
		return empRepository.findAll();
	}
	
	public void saveEmp(Employee emp)
	{
			empRepository.save(emp);
	}
	
	public void deleteEmp(int id) 
	{
		empRepository.deleteById(id);
	}

	public Optional<Employee> getEmployeeById(int id) {
		
		return empRepository.findById(id);
		
	}

	public Employee getEmployeeByEmail(String email) {
		 return empRepository.findByEmail(email);
		 
	}
}
