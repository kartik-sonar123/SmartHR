package com.employeeManagement.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity // Marks the class as a JPA entity to be mapped to a table in the database
@Table(name = "leave_requests") // Specifies the name of the table in the database
public class LeaveRequest {

	@Id // Denotes the primary key of the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID for each new leave request record
	private int id;

	@ManyToOne // Defines a Many-to-One relationship with the Employee entity
	@JoinColumn(name = "employee_id") // Creates the foreign key column 'employee_id' that references the Employee
										// table
	private Employee employee; // The employee who is making the leave request

	private String leaveType; // The type of leave (e.g., sick leave, vacation)

	@DateTimeFormat(pattern = "yyyy-MM-dd") // Formats the date in the specified pattern for the user interface
	private Date startDate; // The start date of the leave request

	@DateTimeFormat(pattern = "yyyy-MM-dd") // Formats the date in the specified pattern for the user interface
	private Date endDate; // The end date of the leave request

	@Column(columnDefinition = "TEXT") // Specifies that the reason field is stored as text
	private String reason; // The reason for the leave request

	@Enumerated(EnumType.STRING) // Specifies that the status is an enum type and should be stored as a string
	private Status status = Status.PENDING; // The status of the leave request (default is PENDING)

	@Column(columnDefinition = "TEXT") // Specifies that the admin comment field is stored as text
	private String adminComment; // Comment from the admin on the leave request (e.g., approval, rejection
									// reason)

	@Temporal(TemporalType.TIMESTAMP) // Specifies that the createdAt field stores both date and time
	private Date createdAt = new Date(); // The date and time when the leave request was created

	// Enum representing the possible statuses of the leave request
	public enum Status {
		PENDING, APPROVED, REJECTED
	}

	// Default constructor
	public LeaveRequest() {
		super();
	}

	// Parameterized constructor to initialize all fields
	public LeaveRequest(int id, Employee employee, String leaveType, Date startDate, Date endDate, String reason,
			Status status, String adminComment, Date createdAt) {
		super();
		this.id = id;
		this.employee = employee;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.status = status;
		this.adminComment = adminComment;
		this.createdAt = createdAt;
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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
