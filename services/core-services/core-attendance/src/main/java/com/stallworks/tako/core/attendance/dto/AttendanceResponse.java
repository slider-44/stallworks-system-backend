package com.stallworks.tako.core.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        Long id,
        Long employeeId,
        Long branchId,
        LocalDate date,
        LocalTime timeIn,
        LocalTime timeOut
) {
    // You can add static factory methods or custom constructors here if needed
}