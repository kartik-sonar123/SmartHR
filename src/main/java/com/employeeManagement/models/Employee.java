package com.employeeManagement.models;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Long phoneNo;

    @Column(nullable = false)
    private String jobRole;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private float salary;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate joiningDate;

    // Default constructor
    public Employee() {
        this.joiningDate = LocalDate.now(); // Auto-set today's date
    }

    // Parameterized constructor (excluding joiningDate to auto-set it)
    public Employee(int id, String name, String email, Long phoneNo, String jobRole,
                    String department, float salary, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.jobRole = jobRole;
        this.department = department;
        this.salary = salary;
        this.address = address;
        this.joiningDate = LocalDate.now(); // Auto-set
    }

    // Getters and Setters
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
    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
}
