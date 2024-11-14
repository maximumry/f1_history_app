package com.f1.f1history.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.f1.f1history.entity.MUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		System.out.println(email + "emailきた");
		//ユーザー情報取得
		MUser loginUser = userService.findLoginUser(email);

		//ユーザーが存在しない場合
		if (loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}

		//権限List作成
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		System.out.println("role" + loginUser.getRole());
		System.out.println("authoritiy" + authorities);

		//UserDetails生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getUserId(),
				loginUser.getPassword(),
				authorities);

		return userDetails;
	}

}
