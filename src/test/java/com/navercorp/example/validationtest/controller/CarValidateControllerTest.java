package com.navercorp.example.validationtest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import com.navercorp.example.validationtest.config.ValidatorConfig;
import com.navercorp.example.validationtest.config.WebAppConfig;
import com.navercorp.example.validationtest.controller.car.CarValidateController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class, ValidatorConfig.class })
public class CarValidateControllerTest {
	@Autowired
	@Qualifier("jsrValidator")
	private Validator validator;
	@Autowired
	private MessageSource messageSource;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CarValidateController(validator, messageSource)).build();
	}

	@Test
	public void directlyValidateTest() throws Exception {
		this.mockMvc.perform(get("/car/validate/directly")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void directlyValidatInvalidParamTest() throws Exception {
		// invalid speed
		this.mockMvc.perform(get("/car/validate/directly")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "4")
							.param("topSpeed", "301"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// invalid seat count
		this.mockMvc.perform(get("/car/validate/directly")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "-1")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// invalid manufacturer
		this.mockMvc.perform(get("/car/validate/directly")
							.characterEncoding("utf-8")
							.param("manufacturer", "")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// not lowercase alphabet manufacturer
		this.mockMvc.perform(get("/car/validate/directly")
							.characterEncoding("utf-8")
							.param("manufacturer", "AbCd")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void automaticallyValidateTest() throws Exception {
		this.mockMvc.perform(get("/car/validate/automatically")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void automaticallyValidatInvalidParamTest() throws Exception {
		// invalid speed
		this.mockMvc.perform(get("/car/validate/automatically")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "4")
							.param("topSpeed", "301"))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// invalid seat count
		this.mockMvc.perform(get("/car/validate/automatically")
							.characterEncoding("utf-8")
							.param("manufacturer", "kook")
							.param("seatCount", "-1")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// invalid manufacturer
		this.mockMvc.perform(get("/car/validate/automatically")
							.characterEncoding("utf-8")
							.param("manufacturer", "")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// not lowercase alphabet manufacturer
		this.mockMvc.perform(get("/car/validate/automatically")
							.characterEncoding("utf-8")
							.param("manufacturer", "AbCd")
							.param("seatCount", "4")
							.param("topSpeed", "200"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
}
