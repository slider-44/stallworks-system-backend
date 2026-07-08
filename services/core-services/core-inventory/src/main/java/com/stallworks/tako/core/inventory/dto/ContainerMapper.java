package com.stallworks.tako.core.inventory.dto;



import org.springframework.stereotype.Component;

import com.stallworks.tako.core.inventory.entity.Container;

@Component
public class ContainerMapper {

	public ContainerResponse toResponse(Container container) {
		
		return new ContainerResponse(
				container.getId(),
				container.getContainerSize(),
				container.getDescription(),
				container.getPieces(),
				container.getPieces() + " PCS",   // piecesLabel, derived
				container.getPrice(),
				container.isActive()
		);
		
	}
}
