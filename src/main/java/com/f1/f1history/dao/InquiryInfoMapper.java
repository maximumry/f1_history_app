package com.f1.f1history.dao;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.Inquiry;

@Mapper
public interface InquiryInfoMapper {
	
	void insertInquiry(Inquiry inquiry);

}
