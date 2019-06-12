package com.navercorp.example.validationtest.controller.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.navercorp.example.validationtest.domain.Person;
import com.navercorp.example.validationtest.validator.PersonValidator;

@Controller
@RequestMapping("person")
public class PersonViewController {
	private static final Logger logger = LoggerFactory.getLogger(PersonViewController.class);

	private final PersonValidator personValidator;

	@Autowired
	public PersonViewController(PersonValidator personValidator) {
		this.personValidator = personValidator;
	}

	@GetMapping("form")
	public String personForm(@ModelAttribute Person person, BindingResult result) {
		logger.debug("person form: {}", person);

		personValidator.validate(person, result);

		return "person";
	}
}
