package com.f1.f1history.controller.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.entity.Comment;
import com.f1.f1history.exception.EventRestResult;
import com.f1.f1history.form.CommentForm;
import com.f1.f1history.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class RestCommentController {

	private final CommentService commentService;
	private final ModelMapper modelMapper;
	private final MessageSource messageSource;

	@GetMapping
	public List<Comment> getAllComment() {
		return commentService.getAllComment();
	}

	@GetMapping("/{eventId}")
	public List<Comment> getComment(@PathVariable String eventId) {
		return commentService.getComment(eventId);
	}

	@PostMapping
	public EventRestResult insertComment(@Validated CommentForm form,
			BindingResult result,
			Locale locale) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();

			//エラーメッセージを取得
			for (FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);

				errors.put(error.getField(), message);
			}
			return new EventRestResult(90, errors, null);
		}
		Comment comment = modelMapper.map(form, Comment.class);
		comment.setUserId(1);
		Comment comment2 = commentService.insertComment(comment);
		return new EventRestResult(0, null, comment2);
	}

	@DeleteMapping("/{commentId}")
	public boolean deleteComment(@PathVariable String commentId) {
		boolean result = commentService.deleteComment(commentId);
		return result;
	}

}
