package com.stallworks.tako.auth.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stallworks.tako.auth.dto.EmployeeRequest;
import com.stallworks.tako.auth.dto.EmployeeResponse;
import com.stallworks.tako.auth.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest request) {
		
		
		EmployeeResponse response = employeeService.create(request);
		
		 URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(response.id())
	                .toUri();
		
		return ResponseEntity.created(location).body(response);
		
		
		
	}
	
	@GetMapping
	 public ResponseEntity<List<EmployeeResponse>> getAll() {
		
		 List<EmployeeResponse> employees = employeeService.getAll();
		 return ResponseEntity.ok(employees);
		 
	 }
	

	

}
