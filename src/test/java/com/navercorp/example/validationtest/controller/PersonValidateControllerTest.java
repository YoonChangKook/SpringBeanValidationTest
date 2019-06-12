package com.navercorp.example.validationtest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.navercorp.example.validationtest.config.ValidatorConfig;
import com.navercorp.example.validationtest.config.WebAppConfig;
import com.navercorp.example.validationtest.controller.person.PersonValidateController;
import com.navercorp.example.validationtest.validator.PersonValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class, ValidatorConfig.class })
public class PersonValidateControllerTest {
	@Autowired
	private PersonValidator personValidator;
	@Autowired
	private MessageSource messageSource;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonValidateController(personValidator, messageSource)).build();
	}

	@Test
	public void directlyValidateTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/directly")
							.param("name", "kook")
							.param("age", "28"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void directlyValidateEmptyNameTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/directly")
							.param("name", "")
							.param("age", "25"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void directlyValidateWrongAgeTest() throws Exception {
		// 음수 나이
		this.mockMvc.perform(get("/person/validate/directly")
							.param("name", "test")
							.param("age", "-1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// 100살을 초과하는 나이
		this.mockMvc.perform(get("/person/validate/directly")
							.param("name", "test")
							.param("age", "101"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void automaticallyValidateTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/automatically")
							.param("name", "kook")
							.param("age", "28"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void automaticallyValidateEmptyNameTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/automatically")
							.param("name", "")
							.param("age", "72"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void automaticallyValidateWrongAgeTest() throws Exception {
		// 음수 나이
		this.mockMvc.perform(get("/person/validate/automatically")
							.param("name", "test")
							.param("age", "-1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// 100살을 초과하는 나이
		this.mockMvc.perform(get("/person/validate/automatically")
							.param("name", "test")
							.param("age", "101"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void noBindingResultValidateTest() throws Exception {
		this.mockMvc.perform(get("/person/validate")
							.param("name", "test")
							.param("age", "-1"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	public void validateAndGetBindingResultTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/get-error-messages")
							.param("name", "")
							.param("age", "99"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
