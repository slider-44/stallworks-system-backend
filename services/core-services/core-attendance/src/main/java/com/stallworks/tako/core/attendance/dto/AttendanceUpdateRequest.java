package com.stallworks.tako.core.attendance.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttendanceUpdateRequest(
        @NotNull LocalTime timeIn,
        LocalTime timeOut,
        @NotBlank String reason,
        @NotNull Long updatedBy
) {}
