package com.f1.f1history.dao;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.User;

@Mapper
public interface UserInfoMapper {
	
	void signup(User user);
	
	User findLoginUser(int userId);

}
