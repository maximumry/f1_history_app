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
		return commentInfoMapper.getAllComment(eventId);
	}

}
