package com.stallworks.tako.core.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.inventory.entity.Container;

public interface ContainerRepository extends JpaRepository<Container, Long>{
	
	List<Container> findAllByActive(boolean isActive);

}
