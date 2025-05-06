package com.employeeManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.UserRepository;
 
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * Save a new user or update an existing user.
	 * 
	 * @param user User object to be saved or updated
	 */
	public void saveUser(User user) {
		userRepository.save(user);
	}

	/**
	 * Fetch a user by their email.
	 * 
	 * @param email Email of the user to be fetched
	 * @return User object if found, null otherwise
	 */
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Delete a user by their ID.
	 * 
	 * @param id ID of the user to be deleted
	 */
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
