package com.employeeManagement.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employeeManagement.models.User;
import com.employeeManagement.repositories.UserRepository;
@Service
public class UserService 
{
	@Autowired
	UserRepository userRepository;
	public void saveUser(User user) 
	{
		userRepository.save(user);
	}
	public User getUserByEmail(String email) 
	{
		
		return userRepository.findByEmail(email);
		
	}
}
