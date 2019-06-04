package com.navercorp.example.validationtest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * DispatcherServlet이 읽어들일 설정을 정의한 클래스
 *
 * @author 국윤창
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.navercorp.example.validationtest"})
public class WebAppConfig {
}
