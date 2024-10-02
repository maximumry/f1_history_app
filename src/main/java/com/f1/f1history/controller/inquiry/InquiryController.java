package com.f1.f1history.controller.inquiry;

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
	public String index(@ModelAttribute Inquiry inquiry, Model model) {
		List<Inquiry> inquiryList = inquiryService.getAllInquiry();
		model.addAttribute("inquiryList", inquiryList);
		return "/inquiry/index";
	}
	
	@GetMapping("/form")
	public String form(@ModelAttribute InquiryForm inquiryForm) {
		return "/inquiry/form";
	}
	
	@PostMapping("/complete")
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
	
	@GetMapping("/{inquiryId}")
	public String getInquiry(@PathVariable int inquiryId, Model model) {
		Inquiry inquiry = inquiryService.getInquiry(inquiryId);
		model.addAttribute("inquiry", inquiry);
		return "/inquiry/detail";
	}

}
