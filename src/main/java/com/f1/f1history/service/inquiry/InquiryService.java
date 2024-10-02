package com.f1.f1history.service.inquiry;

import java.util.List;

import com.f1.f1history.entity.Inquiry;

public interface InquiryService {
	
	void insertInquiry(Inquiry inquiry);
	
	List<Inquiry> getAllInquiry();
	
	Inquiry getInquiry(int inquiryId);
	
	void deleteInquiryOne(int inquiryId);

}
