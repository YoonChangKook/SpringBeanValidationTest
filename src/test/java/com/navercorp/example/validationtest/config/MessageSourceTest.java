package com.navercorp.example.validationtest.config;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebAppConfig.class })
public class MessageSourceTest {
	private static final Logger logger = LoggerFactory.getLogger(MessageSourceTest.class);

	@Autowired
	private MessageSource messageSource;

	@Test
	public void getMessageTest() {
		String message = messageSource.getMessage("name.empty", null, Locale.getDefault());
		logger.debug("name.empty: {}", message);

		message = messageSource.getMessage("too.darn.old", null, Locale.getDefault());
		logger.debug("too.darn.old: {}", message);

		message = messageSource.getMessage("negativevalue", null, Locale.getDefault());
		logger.debug("negativevalue: {}", message);
	}
}
