package com.f1.f1history.exception;

import java.util.Map;

import com.f1.f1history.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRestResult {

	//リターンコード
	private int result;

	/* エラーマップ
	  key: フィールド名
	  value: エラーメッセージ
	 */
	private Map<String, String> errors;

	private Comment comment;
}
