package com.stallworks.tako.service;

import java.util.List;

import com.stallworks.tako.dto.EmployeeRequest;
import com.stallworks.tako.dto.EmployeeResponse;

public interface EmployeeService {
	
	public EmployeeResponse create(EmployeeRequest request);
	
	public List<EmployeeResponse> getAll();

}
