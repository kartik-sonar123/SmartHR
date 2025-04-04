package com.employeeManagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employeeManagement.models.Employee;
import com.employeeManagement.models.User;
import com.employeeManagement.services.EmpService;
import com.employeeManagement.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class UserController
{
	@Autowired
	UserService userService;
	@Autowired
	EmpService empService;
	
	@GetMapping("/register")
	public String viewRegistrationPage(ModelMap map) 
	{
		map.addAttribute("user", new User());
		return "register";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user) 
	{	
		userService.saveUser(user);
		return "login";
	}
	
	
	@GetMapping("/login")
	public String viewLoginPage() 
	{
		return "login";
	}
	
	@PostMapping("/profile")
	public String profile(@RequestParam String email,@RequestParam String password,HttpSession session,ModelMap map)
	{
		 User user= userService.getUserByEmail(email);
		 if(user != null)
		 {
			 if(user.getPassword().equals(password))
			 {
				 session.setAttribute("loggedInUser", user);
				 if(user.getRole().equalsIgnoreCase("admin"))
				 {
			
					 return "redirect:/dashboard"; 
				 }
				 else 
				 {
					 return "redirect:/employee-profile";
				 }	
				
			 }
		 }
		
		 map.addAttribute("error", "incorrect email or password");
		 return "login";
	}
	
	@GetMapping("/employee-profile")
	public String employeeProfile(HttpSession session, ModelMap model) {
	    User loggedInUser = (User) session.getAttribute("loggedInUser");

	    if (loggedInUser == null) {
	        return "redirect:/login"; // Redirect to login if session is empty
	    }

	    // Fetch Employee Details using User Email
	    Employee employee = empService.getEmployeeByEmail(loggedInUser.getEmail());
	    System.out.println(employee);
	    model.addAttribute("employee", employee);
	    return "employee-profile";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();  // Clears session data
	    return "redirect:/login";  // Redirect to login page
	}
}
