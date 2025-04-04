package com.employeeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeManagement.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> 
{
	User findByEmail(String email);
}
