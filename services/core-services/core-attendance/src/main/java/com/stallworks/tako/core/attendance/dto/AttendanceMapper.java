package com.stallworks.tako.core.attendance.dto;



import org.springframework.stereotype.Component;

import com.stallworks.tako.core.attendance.entity.Attendance;

@Component
public class AttendanceMapper {
    
    public Attendance toEntity(AttendanceRequest request) {
	

	return Attendance.builder()
		.employeeId(request.employeeId())
		.branchId(request.branchId())
		.date(request.date())
		.timeIn(request.timeIn())
		.timeOut(request.timeOut())
		.updatedBy(request.updatedBy())
	.build();
	
    }
    
    public AttendanceResponse toResponse(Attendance attendance) {
	
	return new AttendanceResponse(
		attendance.getId(),
		attendance.getEmployeeId(),
		attendance.getBranchId(),
		attendance.getDate(),
		attendance.getTimeIn(),
		attendance.getTimeOut()
		);
    }

}
