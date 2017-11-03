package net.madvirus.spring4.chap07.quickstart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// @Configuration  자바설정 ex) 

@Configuration
@EnableWebMvc
// <mvc:annotation-driven>과 동일하게 스프링 MVC 설정하는데 필요한 빈을 자동 등록해준다.
public class MvcQuickStartConfig {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public HelloController2 helloController() {
		return new HelloController2();
	}

}
