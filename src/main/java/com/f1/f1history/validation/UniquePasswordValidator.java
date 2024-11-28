package com.f1.f1history.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.f1.f1history.dao.UserInfoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniquePasswordValidator implements ConstraintValidator<UniquePassword, String> {

	private final UserInfoMapper userInfoMapper;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return true;
		}

		return value.length() >= 12 && value.length() <= 255;
	}

}
