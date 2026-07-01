package com.stallworks.tako.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stallworks.tako.user.entity.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	boolean existsByPhoneNumber(String phoneNumber);

}
