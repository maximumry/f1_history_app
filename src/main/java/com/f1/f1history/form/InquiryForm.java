package com.f1.f1history.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryForm {
	
	private int inquiryId;
	
	@Pattern(regexp = "driver|iconic|other", message = "いずれかを選択してください")
	@NotBlank(message = "いずれかを選択してください")
	private String inquirySelect;
	
	@NotBlank(message = "名前を入力してください")
	private String name;
	
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "正しいメールアドレスを入力してください")
	private String email;
	
	@NotBlank(message = "内容を入力してください")
	private String content;

}
