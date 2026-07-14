package com.stallworks.tako.core.inventory.dto;



import org.springframework.stereotype.Component;

import com.stallworks.tako.core.inventory.entity.Containers;

@Component
public class ContainerMapper {

	public ContainerResponse toResponse(Containers containers) {
		
		return new ContainerResponse(
				containers.getId(),
				containers.getContainerSize(),
				containers.getDescription(),
				containers.getPieces(),
				containers.getPieces() + " PCS",   // piecesLabel, derived
				containers.getPrice(),
				containers.isActive()
		);
		
	}
}
