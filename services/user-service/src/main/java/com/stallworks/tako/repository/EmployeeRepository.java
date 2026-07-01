package com.stallworks.tako.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stallworks.tako.entity.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	boolean existsByPhoneNumber(String phoneNumber);

}
