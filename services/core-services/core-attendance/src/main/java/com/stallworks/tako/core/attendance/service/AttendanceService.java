package com.stallworks.tako.core.attendance.service;

import java.util.List;
import java.util.Optional;

import com.stallworks.tako.core.attendance.dto.AttendanceRequest;
import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.entity.Attendance;

public interface AttendanceService {

    AttendanceResponse clockIn(Long employeeId, Long branchId);
    AttendanceResponse clockOut(Long employeeId);
    
    AttendanceResponse update(Long id, AttendanceRequest request, Long actorEmployeeId);
    
    Optional<AttendanceResponse> findToday(Long employeeId);
    
    List<AttendanceResponse> findOpenToday();

}
