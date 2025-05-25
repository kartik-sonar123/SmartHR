package com.employeeManagement.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Attendance;
import com.employeeManagement.models.Employee;
import com.employeeManagement.repositories.AttendanceRepository;

@Service
public class AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepo;

	// Mark attendance for an employee
	public boolean markAttendance(Attendance a) {
		// Get current date
		LocalDate today = LocalDate.now();

		// Set attendance date and time to current date and time
		a.setAttendanceDate(today);
		a.setAttendanceTime(LocalTime.now());

		// Set status as Present by default
		a.setStatus("Present");

		// Check if attendance for the employee is already marked today
		boolean alreadyExists = attendanceRepo.findByEmployeeAndAttendanceDate(a.getEmployee(), today).isPresent();

		// If attendance already exists, return false (do not mark again)
		if (alreadyExists) {
			return false; // Already marked
		}

		// Save the attendance record
		attendanceRepo.save(a);

		// Return true indicating attendance marked successfully
		return true;
	}

	// Get count of employees marked present today
	public long getTodayPresentEmployees() {
		// Get current date
		LocalDate today = LocalDate.now();

		// Count attendance records with status "Present" for today
		return attendanceRepo.countByAttendanceDateAndStatus(today, "Present");
	}

	// Retrieve all attendance records along with employee information
	public List<Attendance> getAllAttendances() {
		return attendanceRepo.getAttendanceWithEmployeeInfo();
	}

	// Get attendance records for a specific employee
	public List<Attendance> getAttendanceByEmployee(Employee employee) {
		return attendanceRepo.findByEmployee(employee);
	}
}
