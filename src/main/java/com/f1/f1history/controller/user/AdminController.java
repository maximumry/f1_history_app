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

import com.f1.f1history.entity.MUser;
import com.f1.f1history.form.UpdateMUserForm;
import com.f1.f1history.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserService userService;
	private final ModelMapper modelMapper;

	@GetMapping
	public String getAdmin() {
		return "/admin/admin";
	}

	@GetMapping("/{userId}")
	public String getUser(@PathVariable("userId") String userId,
			Model model) {
		MUser user = userService.getUser(userId);
		UpdateMUserForm userForm = modelMapper.map(user, UpdateMUserForm.class);
		List<String> authorityList = userService.getAuthority();
		userService.keepCurrentLoginUser(userId);
		model.addAttribute("authorityList", authorityList);
		model.addAttribute("updateMUserForm", userForm);
		return "/admin/admin-user-detail";
	}

	@PostMapping(value = "detail", params = "update")
	public String updateUser(@ModelAttribute @Validated UpdateMUserForm updateMUserForm,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> authorityList = userService.getAuthority();
			userService.keepCurrentLoginUser(updateMUserForm.getUserId());
			model.addAttribute("authorityList", authorityList);
			model.addAttribute("updateMUserForm", updateMUserForm);
			return "/admin/admin-user-detail";
		}
		MUser user = modelMapper.map(updateMUserForm, MUser.class);
		userService.updateUser(user);
		return "redirect:/admin";
	}

	@PostMapping(value = "detail", params = "delete")
	public String deleteUser(@ModelAttribute UpdateMUserForm updateMUserForm) {
		MUser user = modelMapper.map(updateMUserForm, MUser.class);
		userService.deleteUser(user);
		return "redirect:/admin";
	}
}
