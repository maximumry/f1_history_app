package com.f1.f1history.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.f1.f1history.validation.UniqueEmail;
import com.f1.f1history.validation.UniquePassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMUserForm {

	@NotNull
	private int userId;

	@NotNull(message = "名前を入力してください")
	@Size(max = 50, message = "50文字以内で入力してください")
	private String name;

	@UniquePassword
	private String password;

	@NotNull(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が間違っています")
	@Size(max = 100, message = "1〜100桁を入力してください")
	@UniqueEmail
	private String email;

	@NotBlank(message = "権限を選択して下さい")
	private String role;
}
