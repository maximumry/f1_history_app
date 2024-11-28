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
@Constraint(validatedBy = UniquePasswordValidator.class)
public @interface UniquePassword {

	String message() default "12文字以上、255文字以内で入力してください";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
