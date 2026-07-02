package com.stallworks.tako.auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_branches", uniqueConstraints = @UniqueConstraint(name = "uq_employee_branch", columnNames = {
		"employee_id", "branch_id" }))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeBranch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@Column(name = "branch_id", nullable = false)
	private Long branchId;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime assignedAt;

	@PrePersist
	protected void onCreate() {
		this.assignedAt = LocalDateTime.now();
	}
}
