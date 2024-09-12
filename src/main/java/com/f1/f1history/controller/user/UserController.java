package com.f1.f1history.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.entity.User;
import com.f1.f1history.form.UserForm;
import com.f1.f1history.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
	
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(@ModelAttribute UserForm form) {
		return "/users/signup";
	}
	
	@PostMapping("/signup")
	public String registerUser(@ModelAttribute @Validated UserForm form,
			BindingResult result) {
		if(result.hasErrors()) {
			return "/users/signup";
		}
		User user = modelMapper.map(form, User.class);
		userService.signup(user);
		return "/users/signup";
	}
	
}
