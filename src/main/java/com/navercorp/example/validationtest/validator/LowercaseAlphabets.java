package com.navercorp.example.validationtest.validator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 파라미터와 필드 검증 시 문자열에 소문자 알파벳만 존재하는 지 확인하기 위한 annotation
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = { LowercaseAlphabetsValidator.class })
public @interface LowercaseAlphabets {
	String message() default "The value must be composed by lowercase alphabets.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
