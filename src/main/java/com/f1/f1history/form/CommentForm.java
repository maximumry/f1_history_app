package com.f1.f1history.form;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

	@NotBlank(message = "不正な操作が検出されました。入力内容をご確認ください")
	private String eventId;

	@NotBlank(message = "コメントを入力してください")
	private String content;

}
