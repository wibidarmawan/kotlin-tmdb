package com.digi.loginapi;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public LoginModel createUser(@RequestParam String email, @RequestParam String name, @RequestParam String dob, @RequestParam String address, @RequestParam String password) {
		LoginModel loginModel = new LoginModel();
		UserModel userModel = userService.createUser(email, name, dob, address, password);
		if (Objects.nonNull(userModel)) {
			loginModel.setStatus("Success");
			loginModel.setUserModelList(userModel);
			return loginModel;
		} else {
			loginModel.setStatus("Failed");
			return loginModel;
		}
	}
	
	@GetMapping("/user/{email}")
	public UserModel getUserByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}
	
	@GetMapping("/login")
	public LoginModel login(@RequestParam String email, @RequestParam String password) {
		LoginModel loginModel = new LoginModel();
		if (userService.login(email, password)) {
			loginModel.setStatus("Success");
			loginModel.setUserModelList(getUserByEmail(email));
			return loginModel;
		} else {
			loginModel.setStatus("Failed");
			return loginModel;
		}
	}
	
	@GetMapping("/user/all")
	public Iterable<UserModel> getAllUser(){
		return userService.getAllUser();
	}
}
