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

	// Method to count attendance records based on a specific date and status
	long countByAttendanceDateAndStatus(LocalDate date, String status);

	// Custom query to fetch attendance records along with the associated employee
	// information
	@Query("SELECT a FROM Attendance a JOIN FETCH a.employee")
	List<Attendance> getAttendanceWithEmployeeInfo();

	// Method to find a specific attendance record for an employee on a given
	// attendance date.
	// This helps in preventing duplication of attendance by checking if a record
	// already exists
	// for the employee on the specified date.
	Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);

}
