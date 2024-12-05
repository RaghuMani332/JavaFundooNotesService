package com.fundoo.notesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class FundooNotesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNotesServiceApplication.class, args);
		System.out.println("started");
	}

}
