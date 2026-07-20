package com.stallworks.tako.core.sales.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="daily_expenses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DailyExpense {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long branchId;

	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false, length = 500)   // adjust length as needed
	private String description;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private Long createdBy;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	

}
