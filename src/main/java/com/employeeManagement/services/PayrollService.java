package com.employeeManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.Payroll;
import com.employeeManagement.repositories.EmpRepository;
import com.employeeManagement.repositories.PayrollRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;  // Repository for Payroll operations

    @Autowired
    private EmpRepository employeeRepository;  // Repository for Employee operations

    /**
     * Generate payroll for a specific employee for a given month.
     * 
     * @param employeeId ID of the employee for whom payroll is to be generated.
     * @param month      Month for which payroll is generated.
     * @return The generated Payroll object.
     */
    public Payroll generatePayroll(int employeeId, String month) {
        // Fetch the employee by ID, throw exception if not found
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        // Calculate basic salary, deductions, and net salary
        double basicSalary = employee.getSalary();
        double deductions = basicSalary * 0.1; // 10% deduction as an example
        double netSalary = basicSalary - deductions;

        // Create a new Payroll object and set the necessary fields
        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setMonth(month);
        payroll.setBasicSalary(basicSalary);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);
        payroll.setGeneratedDate(LocalDate.now()); // Set the current date as the generation date

        // Save the generated payroll in the repository and return it
        return payrollRepository.save(payroll);
    }

    /**
     * Fetch all payroll records for a specific employee.
     * 
     * @param employeeId ID of the employee whose payroll records are to be fetched.
     * @return List of Payroll objects for that employee.
     */
    public List<Payroll> getPayrollsByEmployeeId(int employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);  // Return payrolls by employee ID
    }

    /**
     * Get a summary of payroll for the current month.
     * 
     * @return A map containing the total net salary, processed employee count, and total employee count.
     */
    public Map<String, Object> getMonthlyPayrollSummary() {
        int currentMonth = LocalDate.now().getMonthValue(); // Get the current month
        int currentYear = LocalDate.now().getYear(); // Get the current year

        // Get the total net salary for the current month and year
        Double totalNetSalary = payrollRepository.getTotalNetSalaryForMonth(currentMonth, currentYear);
        Long processedEmployees = payrollRepository.getProcessedEmployeeCount(currentMonth, currentYear);
        Long totalEmployees = employeeRepository.count(); // Get the total number of employees in the system

        // Create a summary map with the data
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalNetSalary", totalNetSalary != null ? totalNetSalary : 0.0); // Handle null total salary
        summary.put("processedEmployees", processedEmployees); // Number of employees whose payroll is processed
        summary.put("totalEmployees", totalEmployees); // Total number of employees

        return summary;
    }

    /**
     * Get the total net salary of all employees across all payroll records.
     * 
     * @return The total net salary of all employees.
     */
    public double getTotalNetSalary() {
        // Sum up the net salary of all payroll records
        return payrollRepository.findAll().stream().mapToDouble(Payroll::getNetSalary).sum();
    }

    /**
     * Get the count of employees whose payroll has been processed.
     * 
     * @return The number of employees whose payroll has been processed.
     */
    public int getProcessedEmployeeCount() {
        // Count the distinct employees who have payroll records
        return (int) payrollRepository.findAll().stream().map(p -> p.getEmployee().getId()).distinct().count();
    }
}
