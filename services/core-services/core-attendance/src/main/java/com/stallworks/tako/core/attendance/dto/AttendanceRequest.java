package com.stallworks.tako.core.attendance.dto;


import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRequest(
        Long employeeId,
        Long branchId,
        LocalDate date,
        LocalTime timeIn,
        LocalTime timeOut,
        Long updatedBy
) {
}