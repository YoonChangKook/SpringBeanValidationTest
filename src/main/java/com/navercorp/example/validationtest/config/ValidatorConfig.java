package com.navercorp.example.validationtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.example.validationtest.validator.PersonValidator;

/**
 * validator를 빈으로 등록하는 클래스
 */
@Configuration
public class ValidatorConfig {
	@Bean
	public PersonValidator personValidator() {
		return new PersonValidator();
	}
}
