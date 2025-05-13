package com.employeeManagement.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.employeeManagement.models.User;
import com.employeeManagement.services.UserService;

@Controller
public class EmployeeController {

	@Autowired
	UserService userService;

	// ======================== EDIT PROFILE PAGE ========================

	// Show the edit form for a user by their ID
	@GetMapping("/userEdit/{id}")
	public String editPage(@PathVariable("id") int id, ModelMap model) {
		Optional<User> optionalUser = userService.getUserById(id);

		// If user not found, redirect to profile page
		if (optionalUser.isEmpty()) {
			return "redirect:/employee-profile";
		}

		// Add existing user data to the model for editing
		model.addAttribute("user", optionalUser.get());
		return "userEdit"; // userEdit.html view
	}

	// ======================== SAVE EDITED PROFILE ========================

	// Handle form submission after user edits their profile
	@PostMapping("/saveUserEdit/{id}")
	public String saveEdit(@PathVariable("id") int id, @ModelAttribute User user) {
		Optional<User> result = userService.getUserById(id);

		if (result.isPresent()) {
			User existingUser = result.get();

			// Update fields with the new values
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhone(user.getPhone());
			existingUser.setAddress(user.getAddress());
			existingUser.setDateOfBirth(user.getDateOfBirth());
			existingUser.setGender(user.getGender());
			existingUser.setAadharNo(user.getAadharNo());
			existingUser.setPanCardNo(user.getPanCardNo());

			// Save the updated user
			userService.saveUser(existingUser);
		}

		// After saving, redirect back to profile page
		return "redirect:/employee-profile";
	}
}
