package com.stallworks.tako.core.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.inventory.entity.Containers;

public interface ContainerRepository extends JpaRepository<Containers, Long>{
	
	List<Containers> findAllByActive(boolean isActive);
	
	Optional<Containers> findByContainerSize(String containerSize);
}
