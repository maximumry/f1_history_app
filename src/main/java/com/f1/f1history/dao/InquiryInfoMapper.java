package com.f1.f1history.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.Inquiry;

@Mapper
public interface InquiryInfoMapper {

	void insertInquiry(Inquiry inquiry);

	List<Inquiry> getAllInquiry();

	Inquiry getInquiry(int inquiryId);

	void deleteInquiryOne(int inquiryId);

	void deleteAndUser(int inquiryid);

	List<Inquiry> selectAllUser(String userId);

}
