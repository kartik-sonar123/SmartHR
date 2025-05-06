package com.employeeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

	// 1. Find all leave requests by employee ID (foreign key)
	List<LeaveRequest> findByEmployeeId(int employeeId);

	// 2. Find all leave requests by Employee object (useful if you have the whole
	// entity)
	List<LeaveRequest> findByEmployee(Employee employee);

	// 3. Count all leave requests that are currently pending approval
	@Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.status = 'PENDING'")
	long countPendingLeave();
}
