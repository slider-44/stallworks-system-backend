package com.stallworks.tako.core.attendance.dto;

import jakarta.validation.constraints.NotNull;

public record ClockOutRequest(@NotNull Long employeeId) {
}