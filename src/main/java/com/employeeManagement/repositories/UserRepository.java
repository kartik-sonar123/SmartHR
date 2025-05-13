package com.employeeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employeeManagement.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// Find a user by their email address
	User findByEmail(String email);

}
