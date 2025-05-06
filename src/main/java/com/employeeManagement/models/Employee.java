package com.employeeManagement.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Marks this class as a JPA entity to be mapped to a database table
public class Employee {

    @Id // Denotes the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID for each new employee record
    private int id;

    @Column(nullable = false) // Ensures that the column cannot have a null value in the database
    private String name;

    @Column(nullable = false) // Ensures that the column cannot have a null value in the database
    private String jobRole;

    @Column(nullable = false) // Ensures that the column cannot have a null value in the database
    private String department;

    @Column(nullable = false) // Ensures that the column cannot have a null value in the database
    private float salary;

    @Column(nullable = false) // Ensures that the column cannot have a null value in the database
    private LocalDate joiningDate; // Represents the date the employee joined the company

    private double allowances; // Represents any additional allowances the employee might have

    @ManyToOne(cascade = CascadeType.ALL) // Defines a Many-to-One relationship with the User entity
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Creates the foreign key column 'user_id' that references the 'id' field of the User table
    private User user; // The User associated with this employee

    // Default constructor
    public Employee() {
        this.joiningDate = LocalDate.now(); // Auto-sets today's date as the default joining date
    }

    // Parameterized constructor to initialize all fields
    public Employee(int id, String name, String jobRole, String department, float salary, LocalDate joiningDate, User user) {
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
}
