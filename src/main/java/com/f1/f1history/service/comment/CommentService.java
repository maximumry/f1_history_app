package com.f1.f1history.service.comment;

import java.util.List;

import com.f1.f1history.entity.Comment;

public interface CommentService {

	Comment insertComment(Comment comment);

	List<Comment> getComment(String eventId);

	boolean deleteComment(String commentId, String string);

	List<Comment> getAllComment();

}
