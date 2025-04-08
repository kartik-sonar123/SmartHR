package com.employeeManagement.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.employeeManagement.models.Employee;
public interface EmpRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String email); 
}
