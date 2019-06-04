package com.navercorp.example.validationtest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.navercorp.example.validationtest.config.ValidatorConfig;
import com.navercorp.example.validationtest.config.WebAppConfig;
import com.navercorp.example.validationtest.validator.PersonValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class, ValidatorConfig.class })
public class PersonValidateControllerTest {
	@Autowired
	private PersonValidator personValidator;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonValidateController(personValidator)).build();
	}

	@Test
	public void directlyValidateTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/directly")
							.content("{\"name\":\"kook\", \"age\":\"28\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void directlyValidateEmptyNameTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/directly")
							.content("{\"name\":\"\", \"age\":\"25\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void directlyValidateWrongAgeTest() throws Exception {
		// 음수 나이
		this.mockMvc.perform(get("/person/validate/directly")
							.content("{\"name\":\"test\", \"age\":\"-1\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// 100살을 초과하는 나이
		this.mockMvc.perform(get("/person/validate/directly")
							.content("{\"name\":\"test\", \"age\":\"101\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void automaticallyValidateTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/automatically")
							.content("{\"name\":\"kook\", \"age\":\"28\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void automaticallyValidateEmptyNameTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/automatically")
							.content("{\"name\":\"\", \"age\":\"72\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}

	@Test
	public void automaticallyValidateWrongAgeTest() throws Exception {
		// 음수 나이
		this.mockMvc.perform(get("/person/validate/automatically")
							.content("{\"name\":\"test\", \"age\":\"-1\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));

		// 100살을 초과하는 나이
		this.mockMvc.perform(get("/person/validate/automatically")
							.content("{\"name\":\"test\", \"age\":\"101\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}
}
