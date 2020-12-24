package com.greenplus.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

	User findByEmail(String email);

	User findByMobileNumber(int mobileNumber);
	
	Optional<User> findBymobileNumber(int mobileNumber);

	List<User> findByRole(String role);

}
