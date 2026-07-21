package com.stallworks.tako.core.attendance.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance",
uniqueConstraints = @UniqueConstraint(name = "uk_attendance_employee_date", columnNames = {"employee_id", "date"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long employeeId;
    
    @Column(nullable = false) 
    private Long branchId;
    
    @Column(nullable = false) 
    private LocalDate date;
    
    private LocalTime timeIn;   // nullable until clocked in
    private LocalTime timeOut; 
    
    private Long updatedBy;     // set when an admin corrects it
    
    @CreationTimestamp 
    private LocalDateTime createdAt;
    
    @UpdateTimestamp 
    private LocalDateTime updatedAt;
    
    

}
