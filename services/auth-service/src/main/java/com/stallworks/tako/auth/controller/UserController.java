package com.stallworks.tako.auth.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stallworks.tako.auth.dto.AccountRequest;
import com.stallworks.tako.auth.dto.AccountResponse;
import com.stallworks.tako.auth.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class UserController {
	
	private final AccountService userService;
	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AccountResponse> create(@RequestBody @Valid AccountRequest userRequest) {
		
		AccountResponse response = userService.createUser(userRequest);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();
		
		return ResponseEntity.created(location).body(response);
		
	}
	
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getUsers() {
	    
	    return ResponseEntity.ok(userService.getUsers());
	    
	}
	
	

}
