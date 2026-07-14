package com.stallworks.tako.core.sales.entity;

import java.math.BigDecimal;

import com.stallworks.tako.core.sales.enums.ContainerSize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sales_line_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesLineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sales_report_id", nullable = false)
	private SalesReport salesReport;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ContainerSize containerSize;   // stored as "SOLO", matches Container.containerSize's string value

	@Column(nullable = false)
	private BigDecimal unitPrice; // snapshot at time of sale
	@Column(nullable = false)
	private Integer quantitySold;
	@Column(nullable = false)
	private BigDecimal lineTotal;

}
