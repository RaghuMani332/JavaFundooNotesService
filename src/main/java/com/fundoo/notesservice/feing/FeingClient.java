package com.fundoo.notesservice.feing;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fundoo.notesservice.responsedto.UserResponse;
import com.fundoo.notesservice.util.ResponceStructure;


@FeignClient("FUNDOOAPIGATEWAY")
public interface FeingClient {
	
	@GetMapping("fundoouserservice/api/v1/user/getuser/{id}")
	public ResponseEntity<ResponceStructure<UserResponse>> getUserById(@PathVariable UUID id);
	
}
