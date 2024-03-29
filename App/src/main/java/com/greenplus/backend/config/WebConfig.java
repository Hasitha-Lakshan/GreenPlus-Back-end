package com.greenplus.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {

		corsRegistry.addMapping("/api/**").allowedOrigins("*").maxAge(3600L).allowedHeaders("*").allowedMethods("*")
				.exposedHeaders("Authorization").allowCredentials(true);
	}

}
