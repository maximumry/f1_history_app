package com.f1.f1history.controller.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.entity.Comment;
import com.f1.f1history.entity.MUser;
import com.f1.f1history.service.comment.CommentService;
import com.f1.f1history.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class RestAdminController {

	private final UserService userService;
	private final CommentService commentService;

	@GetMapping("/users")
	public List<MUser> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/comments")
	public List<Comment> getComments() {
		return commentService.getAllComment();
	}

}
