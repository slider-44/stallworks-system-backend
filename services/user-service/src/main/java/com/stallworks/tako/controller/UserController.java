package com.stallworks.tako.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stallworks.tako.dto.AccountRequest;
import com.stallworks.tako.dto.AccountResponse;
import com.stallworks.tako.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class UserController {
	
	private final AccountService userService;
	
	
	@PostMapping
	public ResponseEntity<AccountResponse> create(@RequestBody @Valid AccountRequest userRequest) {
		
		AccountResponse response = userService.createUser(userRequest);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();
		
		return ResponseEntity.created(location).body(response);
		
	}
	
	

}
