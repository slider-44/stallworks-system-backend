package com.stallworks.tako.core.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.inventory.dto.ContainerMapper;
import com.stallworks.tako.core.inventory.dto.ContainerResponse;
import com.stallworks.tako.core.inventory.entity.Container;
import com.stallworks.tako.core.inventory.repository.ContainerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContainerServiceImp implements ContainerService {

	private final ContainerRepository containerRepository;

	private final ContainerMapper containerMapper;

	@Override
	public List<ContainerResponse> getContainers() {

		List<Container> containers = containerRepository.findAll();

		return containers.stream().map(containerMapper::toResponse).toList();

	}

	@Override
	public List<ContainerResponse> getContainersByActive(boolean isActive) {
		// TODO Auto-generated method stub
		
		List<Container> containers = containerRepository.findAllByActive(isActive);
		
		return containers.stream()
				.map(containerMapper:: toResponse)
				.toList();
	}
	
	

}
