package com.navercorp.example.validationtest.validator;

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
import com.navercorp.example.validationtest.controller.PersonValidateController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class, ValidatorConfig.class })
public class ValidatorTest {
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
							.content("{\"name\":\"test\", \"age\":\"20\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Test
	public void emptyNameTest() throws Exception {
		this.mockMvc.perform(get("/person/validate/directly")
							.content("{\"name\":\"\", \"age\":\"20\"}")
							.contentType("application/json; charset=UTF-8"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("false"));
	}
}
