package com.digi.loginapi;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer>{
	UserModel getUserModelByEmail(String email);	
}
