package com.f1.f1history.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.Inquiry;

@Mapper
public interface InquiryInfoMapper {

	void insertInquiry(Inquiry inquiry);

	List<Inquiry> getAllInquiry();

	Optional<Inquiry> getInquiry(int inquiryId);

	void deleteInquiryOne(int inquiryId);

	void deleteAndUser(int inquiryid);

	List<Inquiry> selectAllUser(String userId);

}
