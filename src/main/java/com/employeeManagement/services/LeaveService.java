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
     * Apply for a new leave request by saving it to the repository.
     * 
     * @param leaveRequest LeaveRequest object containing leave details.
     */
    public void applyLeave(LeaveRequest leaveRequest) {
        leaveRequestRepository.save(leaveRequest);  // Save the leave request in the database.
    }

    /**
     * Fetch all leave requests (used by admin) from the repository.
     * 
     * @return List of all LeaveRequest objects.
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();  // Return all leave requests from the repository.
    }

    /**
     * Update the status and admin's comment of a specific leave request.
     * 
     * @param id      ID of the leave request to update.
     * @param status  New status (e.g., APPROVED, REJECTED).
     * @param comment Admin's comment about the leave decision.
     */
    public void updateStatus(int id, LeaveRequest.Status status, String comment) {
        // Find the leave request by ID, or throw an exception if not found.
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));

        request.setStatus(status);  // Set the new status of the leave request.
        request.setAdminComment(comment);  // Set the admin's comment.
        leaveRequestRepository.save(request);  // Save the updated request in the database.
    }

    /**
     * Fetch leave requests for a specific employee.
     * 
     * @param employee Employee whose leave requests are to be fetched.
     * @return List of LeaveRequest objects for the specified employee.
     */
    public List<LeaveRequest> getLeavesByEmployee(Employee employee) {
        return leaveRequestRepository.findByEmployee(employee);  // Return the leave requests for the given employee.
    }

    /**
     * Get the count of pending leave requests.
     * 
     * @return The number of leave requests that are still pending.
     */
    public long getPendingLeaveCount() {
        return leaveRequestRepository.countPendingLeave();  // Return the count of pending leave requests.
    }
}
