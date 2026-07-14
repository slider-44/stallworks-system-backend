package com.stallworks.tako.core.inventory.entity;

import java.math.BigDecimal;

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
@Table(name = "containers")
@Builder
@Getter
@Setter
@NoArgsConstructor      // <-- JPA requires this
@AllArgsConstructor
public class Containers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String containerSize; // "SOLO" — the stable business key the UI uses

	private String description; // "320CC/450ML"
	
	private int pieces; // 4

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price; // 49.00

	@Builder.Default
	private boolean active = true;


}
