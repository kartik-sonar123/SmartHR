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

	/**
	 * Displays all leave requests to the admin for review URL:
	 * /admin/leave-requests
	 */
	@GetMapping("/admin/leave-requests")
	public String adminLeave(ModelMap model) {
		List<LeaveRequest> leaves = leaveService.getAllLeaveRequests();
		model.addAttribute("leaves", leaves);
		return "admin-leave"; // Corresponds to admin-leave.html
	}

	/**
	 * Handles approval or rejection of leave requests by the admin URL:
	 * /update-status (form action)
	 * 
	 * @param leaveId ID of the leave request to update
	 * @param action  "approve" or "reject"
	 * @param comment Optional admin comment
	 */
	@PostMapping("/update-status")
	public String updateLeaveStatus(@RequestParam int leaveId, @RequestParam String action,
			@RequestParam(required = false) String comment) {

		LeaveRequest.Status status = action.equalsIgnoreCase("approve") ? LeaveRequest.Status.APPROVED
				: LeaveRequest.Status.REJECTED;

		leaveService.updateStatus(leaveId, status, comment);

		return "redirect:/admin/leave-requests";
	}
}
