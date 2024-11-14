package com.f1.f1history.service.user;

import java.util.List;

import com.f1.f1history.entity.MUser;

public interface UserService {

	public void signup(MUser user);

	MUser findLoginUser(String email);

	List<MUser> getUsers();
}
