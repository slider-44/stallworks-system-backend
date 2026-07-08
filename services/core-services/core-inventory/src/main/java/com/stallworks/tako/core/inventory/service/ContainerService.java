package com.stallworks.tako.core.inventory.service;

import java.util.List;

import com.stallworks.tako.core.inventory.dto.ContainerResponse;

public interface ContainerService {
	
	List<ContainerResponse> getContainers();
	
	List<ContainerResponse> getContainersByActive(boolean isActive);
	
	

}
