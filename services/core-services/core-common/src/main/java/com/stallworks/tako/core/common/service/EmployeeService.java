package com.stallworks.tako.core.common.service;

import java.util.List;

import com.stallworks.tako.core.common.dto.EmployeeRequest;
import com.stallworks.tako.core.common.dto.EmployeeResponse;


public interface EmployeeService {
	
	public EmployeeResponse create(EmployeeRequest request);
	
	public List<EmployeeResponse> getAll();

}
