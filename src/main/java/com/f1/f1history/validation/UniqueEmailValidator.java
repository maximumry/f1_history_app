package com.f1.f1history.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.core.context.SecurityContextHolder;

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.entity.MUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserInfoMapper userInfoMapper;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Optional<MUser> mUserOptional = userInfoMapper.findLoginUser(value);

		//リクエストのemailを元にヒットしなかったらバリデーションをかけない
		if (mUserOptional.isEmpty())
			return true;

		if (userDetails instanceof CustomUserDetails) {
			CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
			MUser user = mUserOptional.get();
			System.out.println("送られてきたemail" + user.getEmail());
			System.out.println("ログイン中email" + customUserDetails.getEmail());
			if (user.getEmail().equals(customUserDetails.getEmail())
					&& user.getUserId().equals(customUserDetails.getUserId())) {
				return true;
			}
		}
		return false;
	}

}
