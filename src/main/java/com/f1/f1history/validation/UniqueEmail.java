package com.f1.f1history.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//どこにアノテーションを付与するか意味してる
@Target({ ElementType.METHOD, ElementType.FIELD })
//いつまでアノテーションをどの段階まで実行するか
@Retention(RetentionPolicy.RUNTIME)
//バリデーションを実行するクラスを指定する
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

	String message() default "入力されたメールアドレスはすでに登録されています。別のユーザー名を入力してください";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
