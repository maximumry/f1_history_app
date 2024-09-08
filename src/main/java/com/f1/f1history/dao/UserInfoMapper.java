package com.f1.f1history.dao;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.form.UserForm;

@Mapper
public interface UserInfoMapper {
	
	void singup(UserForm form);

}
