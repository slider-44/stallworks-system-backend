package com.stallworks.tako.core.attendance.dto;

import jakarta.validation.constraints.NotNull;

public record ClockInRequest(
	    @NotNull Long employeeId,
	    @NotNull Long branchId
	) {}