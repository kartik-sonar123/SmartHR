package com.employeeManagement.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Importing necessary libraries from Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Importing model classes
import com.employeeManagement.models.Attendance;
import com.employeeManagement.models.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

	// Find all attendance records for a specific employee
	List<Attendance> findByEmployee(Employee employee);

	// Count the number of attendance records for a given date with a specific
	// status (e.g., "Present")
	long countByAttendanceDateAndStatus(LocalDate date, String status);

	// Custom JPQL query to fetch attendance records along with their associated
	// employee data in one fetch
	@Query("SELECT a FROM Attendance a JOIN FETCH a.employee")
	List<Attendance> getAttendanceWithEmployeeInfo();

	// Find attendance record for a specific employee on a particular date
	// Useful to check if attendance is already marked to avoid duplicates
	Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);
}
