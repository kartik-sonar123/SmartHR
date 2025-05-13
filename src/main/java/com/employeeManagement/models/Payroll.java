package com.employeeManagement.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Marks this class as an entity class for JPA (Java Persistence API)
public class Payroll {

    @Id // This annotation marks the 'id' field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the ID using the identity strategy
    private Long id;

    // Many-to-One relationship with the Employee entity
    @ManyToOne
    @JoinColumn(name = "employee_id") // Foreign key column referring to the employee
    private Employee employee;

    private String month; // The month for which the payroll is generated
    private double basicSalary; // The basic salary of the employee
    private double deductions; // The deductions applied to the salary
    private double netSalary; // The net salary after deductions
    private LocalDate generatedDate; // The date when the payroll was generated

    // Getters and Setters

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public Employee getEmployee() { 
        return employee; 
    }

    public void setEmployee(Employee employee) { 
        this.employee = employee; 
    }

    public String getMonth() { 
        return month; 
    }

    public void setMonth(String month) { 
        this.month = month; 
    }

    public double getBasicSalary() { 
        return basicSalary; 
    }

    public void setBasicSalary(double basicSalary) { 
        this.basicSalary = basicSalary; 
    }

    public double getDeductions() { 
        return deductions; 
    }

    public void setDeductions(double deductions) { 
        this.deductions = deductions; 
    }

    public double getNetSalary() { 
        return netSalary; 
    }

    public void setNetSalary(double netSalary) { 
        this.netSalary = netSalary; 
    }

    public LocalDate getGeneratedDate() { 
        return generatedDate; 
    }

    public void setGeneratedDate(LocalDate generatedDate) { 
        this.generatedDate = generatedDate; 
    }
}
