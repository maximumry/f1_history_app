package com.f1.f1history.service.user;

import com.f1.f1history.entity.User;

public interface UserService {
	
	public void signup(User user);
	
	public User findLoginUser(int userId);

}
