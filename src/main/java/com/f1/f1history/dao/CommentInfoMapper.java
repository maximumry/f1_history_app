package com.f1.f1history.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.f1.f1history.entity.Comment;

@Mapper
public interface CommentInfoMapper {

	void insertComment(Comment comment);

	List<Comment> getEventComment(String eventId);

	Comment getComment(int commentId);

	int deleteComment(String commentId);

	List<Comment> getAllComment();
}
