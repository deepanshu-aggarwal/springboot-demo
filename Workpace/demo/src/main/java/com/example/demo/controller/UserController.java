package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.body.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
		User user = new User();
		user.setName(userRequest.getName());
		user.setAddress(userRequest.getAddress());
		
		userRepo.save(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/get-all")
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
		User existingUser = userRepo.findById(id).orElseThrow();
		
		if(userRequest.getName() != null) {
			existingUser.setName(userRequest.getName());
		}
		if(userRequest.getAddress() != null) {
			existingUser.setAddress(userRequest.getAddress());
		}
		
		User updatedUser = userRepo.save(existingUser);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id) {
		userRepo.deleteById(id);
		return ResponseEntity.ok("Successful");
	}
}
