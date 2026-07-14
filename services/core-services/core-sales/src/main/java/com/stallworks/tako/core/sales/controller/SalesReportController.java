package com.stallworks.tako.core.sales.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;
import com.stallworks.tako.core.sales.service.SalesReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/sales-reports")
@RequiredArgsConstructor
public class SalesReportController {

	private final SalesReportService salesReportService;

	@PostMapping
	public ResponseEntity<SalesReportResponse> create(
			@RequestBody @Valid SalesReportRequest request) {
		return ResponseEntity.ok(salesReportService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<SalesReportResponse>> getAll() {
		return ResponseEntity.ok(salesReportService.findAll());
	}

}
