package com.employeeManagement.models;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // Specifies that this class is an entity and will be mapped to a database table
public class Attendance {

	@Id // Denotes the primary key of the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID for each new record
	private int id;

	@ManyToOne // Establishes a Many-to-One relationship with the Employee entity
	@JoinColumn(name = "emp_id") // Specifies the foreign key column in the Attendance table
	private Employee employee; // The employee who is associated with the attendance record

	private LocalDate attendanceDate; // The date when the attendance was recorded
	private LocalTime attendanceTime; // The time when the attendance was recorded
	private String status; // The attendance status (e.g., 'Present', 'Absent')
	private String remarks; // Any additional remarks regarding the attendance

	// Default constructor
	public Attendance() {
		super();
	}

	// Parameterized constructor to initialize all fields
	public Attendance(int id, Employee employee, LocalDate attendanceDate, LocalTime attendanceTime, String status,
			String remarks) {
		super();
		this.id = id;
		this.employee = employee;
		this.attendanceDate = attendanceDate;
		this.attendanceTime = attendanceTime;
		this.status = status;
		this.remarks = remarks;
	}

	// Getter and Setter methods for each field

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public LocalTime getAttendanceTime() {
		return attendanceTime;
	}

	public void setAttendanceTime(LocalTime attendanceTime) {
		this.attendanceTime = attendanceTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
