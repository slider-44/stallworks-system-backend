package com.stallworks.tako.core.inventory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.inventory.dto.ContainerResponse;
import com.stallworks.tako.core.inventory.service.ContainerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/container-prices")
@RequiredArgsConstructor
public class ContainerController {
	
	private final ContainerService containerService;
	
	@GetMapping
	public ResponseEntity<List<ContainerResponse>> getAllContainers(
			@RequestParam(required=false) Boolean active) {
		
		 List<ContainerResponse> containers = (active != null)
				 ? containerService.getContainersByActive(active)
				 : containerService.getContainers();
		
	
		return ResponseEntity.ok(containers);
		
	
	}
	

}
