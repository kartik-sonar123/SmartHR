package com.employeeManagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employeeManagement.models.LeaveRequest;
import com.employeeManagement.services.LeaveService;

@Controller
public class AdminLeaveController {

	@Autowired
	private LeaveService leaveService;

	// Show all leave requests to admin
	@GetMapping("/admin/leave-requests")
	public String adminLeave(ModelMap model) {
		List<LeaveRequest> leaves = leaveService.getAllLeaveRequests();
		model.addAttribute("leaves", leaves);
		return "admin-leave";
	}

	// Update leave status (approve or reject)
	@PostMapping("/update-status")
	public String updateLeaveStatus(@RequestParam int leaveId,
	                                @RequestParam String action,
	                                @RequestParam(required = false) String comment) {

		LeaveRequest.Status status = action.equalsIgnoreCase("approve")
				? LeaveRequest.Status.APPROVED
				: LeaveRequest.Status.REJECTED;

		leaveService.updateStatus(leaveId, status, comment);
		return "redirect:/admin/leave-requests";
	}
}
