package com.stallworks.tako.core.sales.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sales_reports", 
uniqueConstraints = @UniqueConstraint(
    name = "uk_sales_reports_date_branch", 
    columnNames = {"date", "branch_id"}
))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long employeeId;

	@Column(nullable = false)
	private Long branchId;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime timeIn;

	private LocalTime timeOut;

	@Column(nullable = false)
	private BigDecimal totalSales; // computed server-side

	// ADDED — the other side of SalesLineItem's @ManyToOne
	@OneToMany(mappedBy = "salesReport", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<SalesLineItem> lineItems = new ArrayList<>();
	
	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "updated_by", nullable = false)
	private Long updatedBy;
	
}
