package com.stallworks.tako.core.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    List<Attendance> findByDateAndBranchId(LocalDate date, Long branchId);

    List<Attendance> findByDateAndEmployeeId(LocalDate date, Long employeeId);

    List<Attendance> findByDateAndTimeOutIsNull(LocalDate date);

    List<Attendance> findByEmployeeIdAndDateBetweenOrderByDateDesc(Long employeeId, LocalDate from, LocalDate to);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByDateAndBranchIdAndEmployeeId(LocalDate date, Long branchId, Long employeeId);

    List<Attendance> findByDateBetween(LocalDate from, LocalDate to);

    List<Attendance> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);

}
