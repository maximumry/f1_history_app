package com.f1.f1history.service.user;

import java.util.List;

import com.f1.f1history.entity.MUser;

public interface UserService {

	void signup(MUser user);

	MUser findLoginUser(String email);

	List<MUser> getUsers();

	MUser getUser(String userId);

	void updateUser(MUser user);

	void deleteUser(MUser user);

	List<String> getAuthority();
}
