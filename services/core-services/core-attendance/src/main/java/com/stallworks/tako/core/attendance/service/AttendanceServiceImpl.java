package com.stallworks.tako.core.attendance.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.attendance.dto.AttendanceMapper;
import com.stallworks.tako.core.attendance.dto.AttendanceRequest;
import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.dto.AttendanceUpdateRequest;
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


    @Override
    public List<AttendanceResponse> findOpenToday() {
	 LocalDate today = LocalDate.now(MANILA);

	 return attendanceRepository.findByDateAndTimeOutIsNull(today)
		 .stream()
		 .map(mapper::toResponse)
		 .toList();

    }

    @Override
    public List<AttendanceResponse> findHistory(Long employeeId, LocalDate from, LocalDate to) {
	return attendanceRepository.findByEmployeeIdAndDateBetweenOrderByDateDesc(employeeId, from, to)
		.stream()
		.map(mapper::toResponse)
		.toList();
    }


    @Override
    public void delete(Long id) {
	if (!attendanceRepository.existsById(id)) {
	    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance record not found");
	}
	
	attendanceRepository.deleteById(id);
	
    }


    @Override
    public List<AttendanceResponse> findByFilters(LocalDate date, Long branchId, Long employeeId) {
	List<Attendance> results;

	if (branchId != null && employeeId != null) {
	    results = attendanceRepository.findByDateAndBranchIdAndEmployeeId(date, branchId, employeeId);
	} else if (branchId != null) {
	    results = attendanceRepository.findByDateAndBranchId(date, branchId);
	} else if (employeeId != null) {
	    results = attendanceRepository.findByDateAndEmployeeId(date, employeeId);
	} else {
	    results = attendanceRepository.findByDate(date);
	}
	
	return results.stream()
		.map(mapper::toResponse)
		.toList();
    }



    @Override
    public AttendanceResponse update(Long id, AttendanceUpdateRequest request, Long updatedBy) {
	
	Attendance a = attendanceRepository.findById(id)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance record not found"));
	
	a.setTimeIn(request.timeIn());
	a.setTimeOut(request.timeOut());
	a.setEditReason(request.reason());
	a.setUpdatedBy(request.updatedBy());

	return mapper.toResponse(attendanceRepository.save(a));
		
    }

    @Override
    public void ensureRecordExists(Long employeeId, Long branchId, LocalDate date, LocalTime timeIn, LocalTime timeOut,
	    Long updatedBy) {
	
	if(attendanceRepository.findByEmployeeIdAndDate(employeeId, date).isPresent()) {
	    return;
	}
	
	 Attendance attendance = Attendance.builder()
	            .employeeId(employeeId)
	            .branchId(branchId)
	            .date(date)
	            .timeIn(timeIn)
	            .timeOut(timeOut)
	            .updatedBy(updatedBy)
	            .editReason("Backfilled from Daily Closing Report — no clock-in on record for this date.")
	            .build();

	    attendanceRepository.save(attendance);
	
	
	
    }


    
    


}
