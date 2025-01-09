package com.product.application.hive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.application.hive.helper.PasswordBcrypt;
import com.product.application.hive.model.User;
import com.product.application.hive.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public User registerUser(User user) {
		String hashPassword = PasswordBcrypt.hashPassword(user.getPassword());
        user.setPassword(hashPassword);
        
        return userRepo.save(user);
	}
	
	public Optional<User> getUserById(Long id) {
		return userRepo.findById(id);
	}
	
	public Optional<User> getUserByUsername(String username){
		return userRepo.findByUsername(username);
	}
	
	public User loginUser(String username, String password) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if(optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			if(PasswordBcrypt.checkPassword(password, existingUser.getPassword())) {
				return existingUser;
			}else {
				throw new RuntimeException("Invalid Password");
			}
		}else {
			throw new RuntimeException("user not found with username: " + username);
		}
	}
	
}
