package com.f1.f1history.controller.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.entity.Comment;
import com.f1.f1history.entity.Inquiry;
import com.f1.f1history.entity.MUser;
import com.f1.f1history.form.MUserForm;
import com.f1.f1history.form.UpdateMUserForm;
import com.f1.f1history.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {

	private final ModelMapper modelMapper;
	private final UserService userService;

	@GetMapping("/signup")
	public String signup(@ModelAttribute MUserForm mUserForm) {
		return "/user/signup";
	}

	@PostMapping("/signup")
	public String registerUser(@ModelAttribute @Validated MUserForm mUserForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/user/signup";
		}
		MUser user = modelMapper.map(mUserForm, MUser.class);
		userService.signup(user);
		return "redirect:/driver";
	}

	@GetMapping("/login")
	public String showLoginForm(@ModelAttribute MUserForm mUserForm) {
		return "/user/login";
	}

	@PostMapping("/logout")
	public String logout() {
		return "redirect:/login";
	}

	@GetMapping("/{userId}")
	public String getUser(@PathVariable("userId") String userId,
			Model model) {
		MUser user = userService.getUser(userId);
		List<Comment> commentList = user.getCommentList();
		for (Comment comment : commentList) {
			comment.setUser(user);
			comment.setUserId(user.getUserId());
		}
		List<Inquiry> inquiryList = user.getInquiryList();
		for (Inquiry inquiry : inquiryList) {
			inquiry.setUser(user);
		}
		UpdateMUserForm userForm = modelMapper.map(user, UpdateMUserForm.class);
		model.addAttribute("inquiryList", inquiryList);
		model.addAttribute("commentList", commentList);
		model.addAttribute("MUserForm", userForm);
		return "/user/user-detail";
	}

	@PostMapping(value = "detail", params = "update")
	public String updateUser(@ModelAttribute @Validated UpdateMUserForm mUserForm,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("MUserForm", mUserForm);
			return "/user/user-detail";
		}
		MUser user = modelMapper.map(mUserForm, MUser.class);
		userService.updateUser(user);
		return "redirect:/driver/home";
	}

	@PostMapping(value = "detail", params = "delete")
	public String deleteUser(@ModelAttribute UpdateMUserForm mUserForm) {
		MUser user = modelMapper.map(mUserForm, MUser.class);
		userService.deleteUser(user);
		return "redirect:/driver/home";
	}
}
