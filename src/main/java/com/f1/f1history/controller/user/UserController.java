package com.f1.f1history.controller.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.entity.Comment;
import com.f1.f1history.entity.Inquiry;
import com.f1.f1history.entity.MUser;
import com.f1.f1history.form.MUserForm;
import com.f1.f1history.form.SignupForm;
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
	public String signup(@ModelAttribute SignupForm signupForm) {
		return "/user/signup";
	}

	@PostMapping("/signup")
	public String registerUser(@ModelAttribute @Validated SignupForm signupForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "/user/signup";
		}
		MUser user = modelMapper.map(signupForm, MUser.class);
		userService.signup(user);
		MUserForm mUserForm = modelMapper.map(user, MUserForm.class);
		redirectAttributes.addFlashAttribute("MUserForm", mUserForm);
		return "redirect:/user/login";
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
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		MUser user = userService.getUser(userId);
		if (user.getUserId().equals(userDetails.getUserId())) {
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
			userService.keepCurrentLoginUser(userId);
			model.addAttribute("inquiryList", inquiryList);
			model.addAttribute("commentList", commentList);
			model.addAttribute("updateMUserForm", userForm);
			return "/user/user-detail";
		}
		return "/driver/home";
	}

	@PostMapping(value = "detail", params = "update")
	public String updateUser(@ModelAttribute @Validated UpdateMUserForm updateMUserForm,
			BindingResult result,
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails.getUserId().equals(updateMUserForm.getUserId())) {
			if (result.hasErrors()) {
				userService.keepCurrentLoginUser(updateMUserForm.getUserId());
				return "/user/user-detail";
			}
			MUser user = modelMapper.map(updateMUserForm, MUser.class);
			userService.updateUser(user);
			return "redirect:/driver";
		}
		return "/driver/home";
	}

	@PostMapping(value = "detail", params = "delete")
	public String deleteUser(@ModelAttribute UpdateMUserForm updateMUserForm,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails.getUserId().equals(updateMUserForm.getUserId())) {
			MUser user = modelMapper.map(updateMUserForm, MUser.class);
			userService.deleteUser(user);
			return "redirect:/user/logout";
		}
		return "/driver/home";
	}
}
