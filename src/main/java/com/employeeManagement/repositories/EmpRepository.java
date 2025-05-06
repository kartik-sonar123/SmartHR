package com.employeeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employeeManagement.models.Employee;

public interface EmpRepository extends JpaRepository<Employee, Integer> {
	// 1. Count total number of employees
	@Query("SELECT COUNT(e) FROM Employee e")
	long countTotalEmployees();

	// 2. Fetch all employees with their associated user details to avoid lazy
	// loading issues
	@Query("SELECT e FROM Employee e JOIN FETCH e.user")
	List<Employee> findAllEmployeesWithUser();

	// 3. Find an employee using the associated user's email
	@Query("SELECT e FROM Employee e WHERE e.user.email = :email")
	Employee findByUserEmail(@Param("email") String email);
}
