package com.stallworks.tako.user.service;

import java.util.List;

import com.stallworks.tako.user.dto.EmployeeRequest;
import com.stallworks.tako.user.dto.EmployeeResponse;

public interface EmployeeService {
	
	public EmployeeResponse create(EmployeeRequest request);
	
	public List<EmployeeResponse> getAll();

}
