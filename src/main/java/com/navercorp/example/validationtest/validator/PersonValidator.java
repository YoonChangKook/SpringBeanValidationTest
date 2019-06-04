package com.navercorp.example.validationtest.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.navercorp.example.validationtest.domain.Person;

public class PersonValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		Person p = (Person)obj;
		if (p.getAge() < 0) {
			errors.rejectValue("age", "negativevalue");
		} else if (p.getAge() > 100) {
			errors.rejectValue("age", "too.darn.old");
		}
	}
}
