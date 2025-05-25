package com.employeeManagement.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository; // Repository for User operations

	/**
	 * Save a new user or update an existing user.
	 * 
	 * @param user User object to be saved or updated.
	 * @return
	 */
	public User saveUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		if (userRepository.existsByPhone(user.getPhone())) {
			throw new RuntimeException("Phone number already exists");
		}
		if (userRepository.existsByAadharNo(user.getAadharNo())) {
			throw new RuntimeException("Aadhar number already exists");
		}
		if (userRepository.existsByPanCardNo(user.getPanCardNo())) {
			throw new RuntimeException("PAN card number already exists");
		}
		return userRepository.save(user);
	}

	/**
	 * Fetch a user by their email address.
	 * 
	 * @param email Email of the user to be fetched.
	 * @return User object if found, null otherwise.
	 */
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email); // Fetch the user by their email.
	}

	/**
	 * Delete a user by their ID.
	 * 
	 * @param id ID of the user to be deleted.
	 */
	public void deleteUser(int id) {
		userRepository.deleteById(id); // Delete the user from the repository using the ID.
	}

	/**
	 * Get a user by their ID.
	 * 
	 * @param id ID of the user to be fetched.
	 * @return Optional<User> object containing the user if found, or empty if not.
	 */
	public Optional<User> getUserById(int id) {
		return userRepository.findById(id); // Fetch the user by their ID, wrapped in an Optional.
	}

}
