package com.employeeManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.LeaveRequest;
import com.employeeManagement.repositories.LeaveRequestRepository;

@Service
public class LeaveService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	/**
	 * Apply for a new leave request
	 * 
	 * @param leaveRequest LeaveRequest object containing leave details
	 */
	public void applyLeave(LeaveRequest leaveRequest) {
		leaveRequestRepository.save(leaveRequest);
	}

	/**
	 * Fetch all leave requests (for admin usage)
	 * 
	 * @return List of all LeaveRequest objects
	 */
	public List<LeaveRequest> getAllLeaveRequests() {
		return leaveRequestRepository.findAll();
	}

	/**
	 * Update the status and admin comment for a leave request
	 * 
	 * @param id      ID of the leave request
	 * @param status  New status (APPROVED, REJECTED, etc.)
	 * @param comment Admin's comment regarding the decision
	 */
	public void updateStatus(int id, LeaveRequest.Status status, String comment) {
		LeaveRequest request = leaveRequestRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));

		request.setStatus(status);
		request.setAdminComment(comment);
		leaveRequestRepository.save(request);
	}

	/**
	 * Fetch leave requests for a specific employee
	 * 
	 * @param employee Employee object
	 * @return List of LeaveRequest objects for that employee
	 */
	public List<LeaveRequest> getLeavesByEmployee(Employee employee) {
		return leaveRequestRepository.findByEmployee(employee);
	}

	   public long getPendingLeaveCount() {
	        return leaveRequestRepository.countPendingLeave();
	    }
}
