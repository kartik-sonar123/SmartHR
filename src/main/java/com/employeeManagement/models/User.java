package com.employeeManagement.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity // Marks this class as a JPA entity, indicating it will map to a database table
public class User {
	@Id // Denotes the primary key for this entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID using the identity strategy
	private int id;

	// Basic Information
	@Column(nullable = false) // Marks this column as non-nullable
	private String name;

	@Column(nullable = false, unique = true) // Ensures email is unique and non-null
	private String email;

	@Column(nullable = false) // Ensures password is non-null
	private String password;

	@Column(nullable = false, unique = true) // Ensures phone number is unique and non-null
	private Long phone;

	@Column(nullable = false) // Role is non-null, e.g., admin, user, etc.
	private String role;

	// Contact and Demographic Info
	@Column(nullable = false) // Ensures address is non-null
	private String address;

	private LocalDate dateOfBirth; // Date of birth, using LocalDate for easy date manipulation

	private String gender; // Gender of the user

	// Identity Details
	@Column(unique = true) // Ensures PAN card number is unique
	private String panCardNo;

	@Column(unique = true) // Ensures Aadhar number is unique
	private String aadharNo;

	// One-to-One relationship with Employee, which means each User has one Employee
	@OneToOne
	private Employee employee;

	// Default constructor
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Parameterized constructor to initialize all fields
	public User(int id, String name, String email, String password, Long phone, String role, String address,
			LocalDate dateOfBirth, String gender, String panCardNo, String aadharNo) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.panCardNo = panCardNo;
		this.aadharNo = aadharNo;
	}

	// Getters and Setters for all the fields
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
}
