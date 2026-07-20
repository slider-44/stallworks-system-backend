package com.stallworks.tako.core.attendance.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.attendance.entity.Attendance;

public interface  AttendanceRepository extends JpaRepository<Attendance, Long>{
    
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

}
