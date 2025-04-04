package com.employeeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeManagement.models.Employee;
import java.util.List;


public interface EmpRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String email); 
}
