package com.f1.f1history.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.f1history.dao.CommentInfoMapper;
import com.f1.f1history.entity.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentInfoMapper commentInfoMapper;

	@Override
	public Comment insertComment(Comment comment) {
		commentInfoMapper.insertComment(comment);
		return commentInfoMapper.getComment(comment.getCommentId());
	}

	@Override
	public List<Comment> getComment(String eventId) {
		return commentInfoMapper.getEventComment(eventId);
	}

	@Override
	public boolean deleteComment(String commentId, String userId) {
		Comment comment = commentInfoMapper.getComment(commentId);
		if (userId.equals(comment.getUserId())) {
			int result = commentInfoMapper.deleteComment(commentId);
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Comment> getAllComment() {
		return commentInfoMapper.getAllComment();
	}

}
