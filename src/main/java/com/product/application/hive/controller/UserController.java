package com.product.application.hive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.application.hive.dto.LoginRequest;
import com.product.application.hive.model.User;
import com.product.application.hive.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try {
			if(userService.getUserByUsername(user.getUsername()).isPresent()) {
				return new ResponseEntity<>("User with this ID already exists.", HttpStatus.BAD_REQUEST);
			}
			User savedUser = userService.registerUser(user);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>("User registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> signInUser(@RequestBody LoginRequest request){
		try {
			if(userService.getUserByUsername(request.getUsername()).isPresent()) {
				User loggedInUser = userService.loginUser(request.getUsername(), request.getPassword());
				return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Error while login user", HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>("User login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
