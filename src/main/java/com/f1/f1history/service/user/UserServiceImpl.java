package com.f1.f1history.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.form.UserForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserInfoMapper userInfoMapper;

	@Override
	public void signup(UserForm form) {
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		userInfoMapper.singup(form);
	}

}
