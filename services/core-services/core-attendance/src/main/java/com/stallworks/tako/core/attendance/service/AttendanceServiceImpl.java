package com.stallworks.tako.core.attendance.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.attendance.dto.AttendanceMapper;
import com.stallworks.tako.core.attendance.dto.AttendanceRequest;
import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.entity.Attendance;
import com.stallworks.tako.core.attendance.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    private final AttendanceMapper mapper;
    
    private static final ZoneId MANILA = ZoneId.of("Asia/Manila");

    @Override
    public AttendanceResponse clockIn(Long employeeId, Long branchId) {
	
	LocalDate today = LocalDate.now(MANILA);
	
	if(attendanceRepository.findByEmployeeIdAndDate(employeeId, today).isPresent()) {
	    throw new IllegalStateException("Already clocked in today");
	}
	
	Attendance attendance = Attendance.builder()
	            .employeeId(employeeId)
	            .branchId(branchId).date(today)
	            .timeIn(LocalTime.now())
	            .build();
	
	 Attendance saved = attendanceRepository.save(attendance);
	 
	return mapper.toResponse(saved);
	
	
    }


    @Override
    public AttendanceResponse update(Long id, AttendanceRequest request, Long actorEmployeeId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<AttendanceResponse> findToday(Long employeeId) {
	
	return attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now(MANILA))
		.map(mapper:: toResponse);
    }


    @Override
    public AttendanceResponse clockOut(Long employeeId) {
	 Attendance a = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now(MANILA))
		 .orElseThrow(() -> new IllegalStateException("Not clocked in yet"));
	 
	 if (a.getTimeOut() != null) throw new IllegalStateException("Already clocked out today");
	 a.setTimeOut(LocalTime.now(MANILA));
	 return mapper.toResponse(attendanceRepository.save(a));
	 
	
    }

//
//	// Admin correction — manually override either time
//	public AttendanceResponse update(Long id, AttendanceUpdateRequest request, Long actorEmployeeId) {
//	    Attendance a = attendanceRepository.findById(id).orElseThrow();
//	    if (request.timeIn() != null) a.setTimeIn(request.timeIn());
//	    if (request.timeOut() != null) a.setTimeOut(request.timeOut());
//	    a.setUpdatedBy(actorEmployeeId);
//	    return mapper.toResponse(attendanceRepository.save(a));
//	}


}
