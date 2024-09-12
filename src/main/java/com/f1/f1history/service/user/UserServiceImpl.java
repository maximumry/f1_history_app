package com.f1.f1history.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserInfoMapper userInfoMapper;

	@Override
	public void signup(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userInfoMapper.signup(user);
	}

	@Override
	public User findLoginUser(int userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
