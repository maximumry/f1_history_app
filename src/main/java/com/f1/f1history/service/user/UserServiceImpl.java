package com.f1.f1history.service.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.entity.MUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final PasswordEncoder encoder;
	private final UserInfoMapper userInfoMapper;

	@Override
	public void signup(MUser user) {
		user.setRole("general");

		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		userInfoMapper.signup(user);
	}

	@Override
	public MUser findLoginUser(String email) {
		return userInfoMapper.findLoginUser(email);
	}

	@Override
	public List<MUser> getUsers() {
		return userInfoMapper.getUsers();
	}

}
