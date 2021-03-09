package com.digi.loginapi;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository userRepository;
	
	@Autowired
	private UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserModel createUser(String email, String name, String dob, String address, String password) {
		UserModel userModel = new UserModel();
		
		if (Objects.nonNull(userRepository.getUserModelByEmail(email))) {
			return null;
		} else {
			userModel.setEmail(email);
			userModel.setName(name);
			userModel.setDob(dob);
			userModel.setAddress(address);
			userModel.setPassword(password);
			
			return userRepository.save(userModel);
		}
	}
	
	public Optional<UserModel> getUser(int id){
		return userRepository.findById(id);
	}
	
	public UserModel getUserByEmail(String email){
		return userRepository.getUserModelByEmail(email);
	}
	
	public Boolean login(String email, String password) {
		UserModel userModel = userRepository.getUserModelByEmail(email);
		if (Objects.nonNull(userModel)) {
			if(userModel.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Iterable<UserModel> getAllUser(){
		return userRepository.findAll();
	}
}
