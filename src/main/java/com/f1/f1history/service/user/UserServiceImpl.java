package com.f1.f1history.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.f1history.dao.CommentInfoMapper;
import com.f1.f1history.dao.InquiryInfoMapper;
import com.f1.f1history.dao.UserInfoMapper;
import com.f1.f1history.entity.Comment;
import com.f1.f1history.entity.Inquiry;
import com.f1.f1history.entity.MUser;
import com.f1.f1history.service.comment.CommentService;
import com.f1.f1history.service.inquiry.InquiryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final PasswordEncoder encoder;
	private final UserInfoMapper userInfoMapper;
	private final CommentService commentService;
	private final CommentInfoMapper commentInfoMapper;
	private final InquiryService inquiryService;
	private final InquiryInfoMapper inquiryInfoMapper;
	private String keepUserId = "";

	@Override
	public void signup(MUser user) {
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		userInfoMapper.signup(user);
	}

	@Override
	public MUser findLoginUser(String email) {
		return userInfoMapper.findLoginUser(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Override
	public List<MUser> getUsers() {
		return userInfoMapper.getUsers();
	}

	@Override
	public MUser getUser(String userId) {
		return userInfoMapper.getUser(userId);

	}

	@Override
	public void updateUser(MUser mUser) {
		//パスワード変更がされない(フォームが空で送信された)場合はuserIdを元にパスワードを取得してmUserオブジェクトに取得したパスワードをセットしてる
		if (mUser.getPassword() == null || mUser.getPassword().isEmpty()) {
			String userIdStr = String.valueOf(mUser.getUserId());
			String password = userInfoMapper.getUserPassword(userIdStr);
			mUser.setPassword(password);
		} else {
			String rawPassword = mUser.getPassword();
			mUser.setPassword(encoder.encode(rawPassword));
		}
		userInfoMapper.updateUser(mUser);
	}

	@Override
	public void deleteUser(MUser user) {
		List<Comment> comments = commentInfoMapper.selectAllUser(user.getUserId());
		if (comments != null) {
			for (Comment comment : comments) {
				commentService.deleteComment(comment.getCommentId(), user.getUserId());
			}
		}
		List<Inquiry> inquiries = inquiryInfoMapper.selectAllUser(user.getUserId());
		if (inquiries != null) {
			for (Inquiry inquiry : inquiries) {
				inquiryService.deleteAndUser(inquiry.getInquiryId());
			}
		}
		userInfoMapper.deleteUser(user);
	}

	@Override
	public List<String> getAuthority() {
		List<String> authorityList = new ArrayList<String>();

		authorityList.add("ADMIN");
		authorityList.add("GENERAL");

		return authorityList;
	}

	@Override
	public void keepCurrentLoginUser(String userId) {
		keepUserId = userId;
	}

	@Override
	public String getCurrentLoginUser() {
		return keepUserId;
	}

}
