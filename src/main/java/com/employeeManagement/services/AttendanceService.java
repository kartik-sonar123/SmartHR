package com.employeeManagement.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Attendance;
import com.employeeManagement.repositories.AttendanceRepository;

@Service
public class AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepo;

	// Mark attendance for an employee
	public boolean markAttendance(Attendance a) {
		LocalDate today = LocalDate.now();
		a.setAttendanceDate(today);
		a.setAttendanceTime(LocalTime.now());
		a.setStatus("Present");

		// Check if attendance is already marked for today
		boolean alreadyExists = attendanceRepo.findByEmployeeAndAttendanceDate(a.getEmployee(), today).isPresent();

		if (alreadyExists) {
			return false; // Already marked
		}

		attendanceRepo.save(a); // Save attendance
		return true;
	}

	// Get number of employees present today
	public long getTodayPresentEmployees() {
		LocalDate today = LocalDate.now();
		return attendanceRepo.countByAttendanceDateAndStatus(today, "Present");
	}

	// Get all attendance records with employee info
	public List<Attendance> getAllAttendances() {
		return attendanceRepo.getAttendanceWithEmployeeInfo();
	}
}
