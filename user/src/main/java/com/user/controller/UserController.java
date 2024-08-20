package com.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;

@CrossOrigin(origins ="*")
@RequestMapping("/user")
@RestController
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("/get")
	public String home() {
        String message = "Welcome to user Spring Boot CRUD";
        System.out.println(message);
        return message;
    }
	@PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
	@GetMapping("/getUser")
    public List<User> getAllUsers() {
        
        List<User> users = userService.getAllUsers();

       
        System.out.println("Fetching all users:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Password: " + user.getPassword());
        }

        return users;
    }

	@GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {

        Optional<User> user = userService.getUserById(id);
        
        
        if (user.isPresent()) {
            User foundUser = user.get();
            System.out.println("User found: ID = " + foundUser.getId() + ", Name = " + foundUser.getName() + ", Password = " + foundUser.getPassword());
            return ResponseEntity.ok(foundUser);
        } else {
            System.out.println("User with ID " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
    }

	 @PutMapping("/update/{id}")
	    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
	        Optional<User> user = userService.getUserById(id);
	        if (user.isPresent()) {
	            User updatedUser = user.get();
	            updatedUser.setId(userDetails.getId());
	            updatedUser.setName(userDetails.getName());
	            updatedUser.setPassword(userDetails.getPassword());
	            userService.saveUser(updatedUser);
	            return ResponseEntity.ok(updatedUser);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
	        if (userService.getUserById(id).isPresent()) {
	            userService.deleteUser(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }

	 
		
	 }
}
	

