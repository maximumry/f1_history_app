package com.f1.f1history.controller.inquiry;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping
	public String form(@ModelAttribute InquiryForm inquiryForm) {
		return "/inquiry/form";
	}
	
	@PostMapping
	public String complete(@ModelAttribute @Validated InquiryForm form,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			return "/inquiry/form";
		}
		Inquiry inquiry = modelMapper.map(form, Inquiry.class);
		inquiryService.insertInquiry(inquiry);
		return "redirect:/driver";
	}
	
	

}
