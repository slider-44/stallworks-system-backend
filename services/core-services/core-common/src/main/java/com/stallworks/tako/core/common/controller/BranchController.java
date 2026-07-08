package com.stallworks.tako.core.common.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.common.dto.BranchResponse;
import com.stallworks.tako.core.common.service.BranchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

	private final BranchService branchService;
	
	@GetMapping
	public ResponseEntity<List<BranchResponse>> getAllBranch(
			@RequestParam(required=false) Boolean active) {
		
		
		List<BranchResponse> response = (active != null)
				? branchService.getAllBranchByActive(active)
				: branchService.getAllBranch();
		
		
		return ResponseEntity.ok(response);
		
	}
}
