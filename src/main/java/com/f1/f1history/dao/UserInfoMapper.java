package com.f1.f1history.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.MUser;

@Mapper
public interface UserInfoMapper {

	void signup(MUser user);

	MUser findLoginUser(String email);

	List<MUser> getUsers();

}
