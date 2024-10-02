package com.f1.f1history.controller.inquiry;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.form.InquiryForm;
import com.f1.f1history.service.inquiry.InquiryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class RestInquiryController {
	
	private final InquiryService inquiryService;
	
	@DeleteMapping("/delete")
	public int deleteInquiry(InquiryForm form) {
		inquiryService.deleteInquiryOne(form.getInquiryId());
		return 0;
	}

}
