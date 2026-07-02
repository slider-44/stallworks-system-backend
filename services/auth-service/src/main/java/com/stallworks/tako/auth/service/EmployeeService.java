package com.stallworks.tako.auth.service;

import java.util.List;

import com.stallworks.tako.auth.dto.EmployeeRequest;
import com.stallworks.tako.auth.dto.EmployeeResponse;

public interface EmployeeService {
	
	public EmployeeResponse create(EmployeeRequest request);
	
	public List<EmployeeResponse> getAll();

}
