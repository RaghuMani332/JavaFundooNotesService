package com.fundoo.notesservice.feing;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fundoo.notesservice.exception.UserServiceNotAvailableException;
import com.fundoo.notesservice.responsedto.UserResponse;
import com.fundoo.notesservice.util.ResponceStructure;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@FeignClient("FUNDOOAPIGATEWAY")
public interface FeingClient {
	
	@GetMapping("api/v1/user/getuser/{id}")
	@CircuitBreaker(name="feing" , fallbackMethod = "fallbackuser")
	public ResponseEntity<ResponceStructure<UserResponse>> getUserById(@PathVariable(name = "id") UUID id);

	
	default ResponseEntity<ResponceStructure<UserResponse>> fallbackuser(Exception e)
	{
		System.out.println("from user fall back");
		throw new UserServiceNotAvailableException("User Service Unavailable please try later");

	}
	
	
}

