package com.navercorp.example.validationtest.controller.person;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.example.validationtest.domain.Car;

@RestController
@RequestMapping("car")
public class CarValidateController {
	private static final Logger logger = LoggerFactory.getLogger(CarValidateController.class);

	private final Validator validator;

	@Autowired
	public CarValidateController(@Qualifier("jsrValidator") Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return Car 객체 검증 성공 여부
	 */
	@GetMapping("validate/directly")
	public boolean directlyValidateCar(@ModelAttribute Car car, BindingResult result) {
		logger.debug("validate directly. {}", car);

		validator.validate(car, result);
		logger.debug("errors: {}", result.getFieldErrors());

		return !result.hasErrors();
	}

	/**
	 * @return Car 객체 검증 성공 여부
	 */
	@GetMapping("validate/automatically")
	public boolean automaticallyValidateCar(@ModelAttribute @Valid Car car) {
		logger.debug("validate automatically. {}", car);

		return true;
	}

	/**
	 * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
	 */
	@ExceptionHandler({BindException.class})
	public ResponseEntity<String> paramViolationError(BindException ex) {
		logger.error(ex.getMessage());
		return ResponseEntity.badRequest().body(ex.getFieldError().getDefaultMessage());
	}
}
