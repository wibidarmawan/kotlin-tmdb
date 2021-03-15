package com.digi.loginapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import antlr.collections.List;

public interface UserRepository extends CrudRepository<UserModel, Integer>{
	UserModel getUserModelByEmail(String email);
	
	@Query("select u.email from UserModel u")
	java.util.List<String> getEmail();
}
