package com.f1.f1history.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.f1.f1history.entity.MUser;

@Mapper
public interface UserInfoMapper {

	void signup(MUser mUser);

	Optional<MUser> findLoginUser(String email);

	List<MUser> getUsers();

	MUser getUser(String userId);

	void updateUser(@Param("mUser") MUser mUser);

	void deleteUser(@Param("mUser") MUser user);

	String getUserPassword(String userId);

}
