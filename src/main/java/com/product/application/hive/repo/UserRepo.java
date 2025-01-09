package com.product.application.hive.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.application.hive.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
