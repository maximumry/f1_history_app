package com.f1.f1history.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.core.context.SecurityContextHolder;

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.entity.MUser;
import com.f1.f1history.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserInfoMapper userInfoMapper;
	private final UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Optional<MUser> mUserOptional = userInfoMapper.findLoginUser(value);
		if (mUserOptional.isPresent()) {
			return userService.emailUniqueForUpdate(mUserOptional.get(), userDetails);
		}
		return mUserOptional.isEmpty();
	}

}
