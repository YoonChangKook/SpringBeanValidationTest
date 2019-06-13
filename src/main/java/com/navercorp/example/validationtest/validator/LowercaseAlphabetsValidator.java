package com.navercorp.example.validationtest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * LowercaseAlphabets 애노테이션이 붙은 필드 혹은 파라미터가 소문자 알파벳으로만 이루어져 있는지 확인한다.
 */
public class LowercaseAlphabetsValidator implements ConstraintValidator<LowercaseAlphabets, String> {
	private static final int LOWER_CASE_A_ASCII = (int)'a';
	private static final int LOWER_CASE_Z_ASCII = (int)'z';

	@Override
	public void initialize(LowercaseAlphabets constraintAnnotation) {}

	/**
	 * 입력받은 문자열이 소문자 알파벳으로만 이루어져 있는지 검사하는 메서드
	 * @param value 검사할 문자열
	 * @param context constraint context
	 * @return 검증 결과
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c < LOWER_CASE_A_ASCII || LOWER_CASE_Z_ASCII < c) {
				return false;
			}
		}

		return true;
	}
}
