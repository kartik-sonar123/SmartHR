package com.employeeManagement.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // Marks this class as a JPA entity, which will be mapped to a database table
public class Employee {

	@Id // Denotes that the 'id' field is the primary key of this entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the ID value for each new employee record
	private int id;

	@Column(nullable = false) // Ensures the 'name' field cannot be null in the database
	private String name;

	@Column(nullable = false) // Ensures the 'jobRole' field cannot be null in the database
	private String jobRole;

	@Column(nullable = false) // Ensures the 'department' field cannot be null in the database
	private String department;

	@Column(nullable = false) // Ensures the 'salary' field cannot be null in the database
	private float salary;

	@Column(nullable = false) // Ensures the 'joiningDate' field cannot be null in the database
	private LocalDate joiningDate; // The date when the employee joined the company

	private double allowances; // Represents additional allowances the employee might have (optional)

	// Many-to-One relationship with the 'User' entity, indicating an employee can be associated with one user
	@ManyToOne(cascade = CascadeType.ALL) // Cascade all operations on 'Employee' to the associated 'User'
	@JoinColumn(name = "user_id", referencedColumnName = "id") // Creates the foreign key 'user_id' referencing 'User' table's 'id'
	private User user; // The User entity associated with this Employee

	// One-to-Many relationship with the 'Attendance' entity (one employee can have multiple attendance records)
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attendance> attendanceList = new ArrayList<>();

	// One-to-Many relationship with the 'Payroll' entity (one employee can have multiple payroll records)
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Payroll> payrolls = new ArrayList<>();

	// One-to-Many relationship with the 'LeaveRequest' entity (one employee can have multiple leave requests)
	@OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE) // Removes associated leave requests when an employee is deleted
	private List<LeaveRequest> leaveRequests;

	// Default constructor that sets the joining date to today's date
	public Employee() {
		this.joiningDate = LocalDate.now(); // Default joining date as the current date
	}

	// Parameterized constructor for initializing all fields
	public Employee(int id, String name, String jobRole, String department, float salary, LocalDate joiningDate,
			User user) {
		super();
		this.id = id;
		this.name = name;
		this.jobRole = jobRole;
		this.department = department;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.user = user;
	}

	// Getter and Setter methods for each field

	public double getAllowances() {
		return allowances;
	}

	public void setAllowances(double allowances) {
		this.allowances = allowances;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Overridden toString() method for displaying the Employee object as a string
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", jobRole=" + jobRole + ", department=" + department
				+ ", salary=" + salary + ", joiningDate=" + joiningDate + ", allowances=" + allowances + ", user="
				+ user + "]";
	}

}
