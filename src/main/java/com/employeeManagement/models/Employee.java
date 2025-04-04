package com.employeeManagement.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(nullable=false,unique=true)
	private String email;
	@Column(nullable=false,unique = true)
	private Long phoneNo;
	@Column(nullable=false)
	private String jobRole;
	@Column(nullable=false)
	private float salary;
	@Column(nullable=false)
	private String address;
	
	
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int id, String name, String email, Long phoneNo, String jobRole, float salary, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.jobRole = jobRole;
		this.salary = salary;
		this.address = address;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public Long getPhoneNo() 
	{
		return phoneNo;
	}
	public void setPhoneNo(Long phoneNo) 
	{
		this.phoneNo = phoneNo;
	}
	public String getJobRole() 
	{
		return jobRole;
	}
	public void setJobRole(String jobRole) 
	{
		this.jobRole = jobRole;
	}
	public float getSalary() 
	{
		return salary;
	}
	public void setSalary(float salary) 
	{
		this.salary = salary;
	}
	public String getAddress() 
	{
		return address;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}	
}
