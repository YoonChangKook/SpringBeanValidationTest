package com.navercorp.example.validationtest.controller.person;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.example.validationtest.domain.Person;
import com.navercorp.example.validationtest.validator.PersonValidator;

@RestController
@RequestMapping("person")
public class PersonValidateController {
	private static final Logger logger = LoggerFactory.getLogger(PersonValidateController.class);

	private final PersonValidator personValidator;
	private final MessageSource messageSource;

	@Autowired
	public PersonValidateController(PersonValidator personValidator, MessageSource messageSource) {
		this.personValidator = personValidator;
		this.messageSource = messageSource;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(personValidator);
	}

	/**
	 * @return person 객체의 검증 성공 여부
	 */
	@GetMapping("validate/directly")
	public boolean directlyValidatePerson(@RequestBody Person person, BindingResult result) {
		logger.debug("validate directly. {}", person);

		personValidator.validate(person, result);

		return !result.hasErrors();
	}

	/**
	 * @return person 객체의 검증 성공 여부
	 */
	@GetMapping("validate/automatically")
	public boolean automaticallyValidatePerson(@RequestBody @Valid Person person, BindingResult result) {
		logger.debug("validate automatically. {}", person);

		return !result.hasErrors();
	}

	/**
	 * @return person 객체의 검증 후 에러 메시지들
	 */
	@GetMapping("validate/get-error-messages")
	public List<String> validateAndGetErrorMessages(@RequestBody Person person, BindingResult result) {
		logger.debug("validated. {}", person);

		personValidator.validate(person, result);

		return result.getFieldErrors().stream()
									.map(error -> messageSource.getMessage(error, Locale.getDefault()))
									.collect(Collectors.toList());
	}

	@GetMapping("validate")
	public void noBindingResultValidatePerson(@RequestBody @Valid Person person) {
		logger.debug("validated. {}", person);
	}
}
