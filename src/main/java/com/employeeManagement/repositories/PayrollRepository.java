package com.employeeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employeeManagement.models.Payroll;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	// Custom query to find all payrolls for an employee by their ID
	List<Payroll> findByEmployeeId(int employeeId);

	// Custom query to calculate the total net salary for a given month and year
	@Query("SELECT SUM(p.netSalary) FROM Payroll p WHERE FUNCTION('MONTH', p.generatedDate) = :month AND FUNCTION('YEAR', p.generatedDate) = :year")
	Double getTotalNetSalaryForMonth(@Param("month") int month, @Param("year") int year);

	// Custom query to count the distinct number of employees who have payroll records for a given month and year
	@Query("SELECT COUNT(DISTINCT p.employee.id) FROM Payroll p WHERE FUNCTION('MONTH', p.generatedDate) = :month AND FUNCTION('YEAR', p.generatedDate) = :year")
	Long getProcessedEmployeeCount(@Param("month") int month, @Param("year") int year);
}
