package com.navercorp.example.validationtest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public PersonValidateController(PersonValidator personValidator) {
		this.personValidator = personValidator;
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
}
