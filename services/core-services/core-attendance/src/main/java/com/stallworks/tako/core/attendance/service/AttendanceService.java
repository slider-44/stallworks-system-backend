package com.stallworks.tako.core.attendance.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.dto.AttendanceUpdateRequest;

public interface AttendanceService {

    AttendanceResponse clockIn(Long employeeId, Long branchId);
    AttendanceResponse clockOut(Long employeeId);
    void delete(Long id);
    AttendanceResponse update(Long id, AttendanceUpdateRequest request, Long updateBy);
    Optional<AttendanceResponse> findToday(Long employeeId);
    List<AttendanceResponse> findOpenToday();
    List<AttendanceResponse> findHistory(Long employeeId, LocalDate from, LocalDate to);
    // Backs the Time Records admin table — date is always required (the
    // filter bar always has one selected), branch/employee narrow it down.
    List<AttendanceResponse> findByFilters(LocalDate date, Long branchId, Long employeeId);
    
     // Backfills a missing Attendance row from a Sales Entry save (e.g. an
     // admin backdating a shift). Never touches an existing row — a real
     // clock-in/out (or a prior admin edit) always wins; corrections to those
     // still go through update(), which requires a reason.
    void ensureRecordExists(Long employeeId, Long branchId, LocalDate date, LocalTime timeIn, LocalTime timeOut, Long updatedBy);


}
