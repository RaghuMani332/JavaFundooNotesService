package com.fundoo.notesservice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Retryer;

@Configuration
public class Config {

	@Bean
	public Feign.Builder feignBuilder() {
	    return Feign.builder()
	                .retryer(new Retryer.Default(1000, 5000, 3));  // Retry 3 times, with 1-5 seconds interval
	}

}
