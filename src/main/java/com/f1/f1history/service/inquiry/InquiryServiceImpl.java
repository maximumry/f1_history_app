package com.f1.f1history.service.inquiry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.f1history.dao.InquiryInfoMapper;
import com.f1.f1history.entity.Inquiry;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
	
	private final InquiryInfoMapper inquiryInfoMapper;

	@Override
	public void insertInquiry(Inquiry inquiry) {
		inquiry.setUserId("1");
		inquiryInfoMapper.insertInquiry(inquiry);
	}

	@Override
	public List<Inquiry> getAllInquiry() {
		return inquiryInfoMapper.getAllInquiry();
	}

	@Override
	public Inquiry getInquiry(int inquiryId) {
		return inquiryInfoMapper.getInquiry(inquiryId);
	}

	@Override
	public void deleteInquiryOne(int inquiryId) {
		inquiryInfoMapper.deleteInquiryOne(inquiryId);
	}

}
