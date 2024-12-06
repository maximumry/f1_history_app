package com.f1.f1history.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.core.GrantedAuthority;
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
		Object userDetails = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Optional<MUser> mUserOptional = userInfoMapper.findLoginUser(value);

		//リクエストのemailを元にヒットしなかったらバリデーションをかけない
		if (mUserOptional.isEmpty())
			return true;

		if (userDetails != "anonymousUser") {
			CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
			MUser user = mUserOptional.get();
			String requestUserId = userService.getCurrentLoginUser();
			String authority = "";
			for (GrantedAuthority forInAuthority : customUserDetails.getAuthorities()) {
				authority = forInAuthority.getAuthority();
			}

			if (requestUserId.equals(customUserDetails.getUserId())
					&& requestUserId.equals(user.getUserId())
					&& user.getUserId().equals(customUserDetails.getUserId())
					|| requestUserId.equals(user.getUserId()) && authority.equals("ADMIN")) {
				return true;
			}
		}
		return false;
	}

}
