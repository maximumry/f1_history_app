package com.f1.f1history.controller.inquiry;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.entity.Inquiry;
import com.f1.f1history.form.InquiryForm;
import com.f1.f1history.service.inquiry.InquiryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

	private final ModelMapper modelMapper;
	private final InquiryService inquiryService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String index(@ModelAttribute Inquiry inquiry, Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userId", userDetails.getUserId());
		List<Inquiry> inquiryList = inquiryService.getAllInquiry();
		model.addAttribute("inquiryList", inquiryList);
		return "/inquiry/index";
	}

	@GetMapping("/form")
	public String form(@ModelAttribute InquiryForm inquiryForm,
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userName", userDetails.getUsername());
		return "/inquiry/form";
	}

	@PostMapping("/complete")
	public String complete(@ModelAttribute @Validated InquiryForm form,
			BindingResult result,
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (result.hasErrors()) {
			return "/inquiry/form";
		}
		Inquiry inquiry = modelMapper.map(form, Inquiry.class);
		inquiry.setUserId(userDetails.getUserId());
		inquiryService.insertInquiry(inquiry);
		return "redirect:/driver";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{inquiryId}")
	public String getInquiry(@PathVariable int inquiryId, Model model) {
		Inquiry inquiry = inquiryService.getInquiry(inquiryId);
		model.addAttribute("inquiry", inquiry);
		return "/inquiry/detail";
	}

}
