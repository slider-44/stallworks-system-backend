package com.stallworks.tako.core.common.service;

import java.util.List;

import com.stallworks.tako.core.common.dto.BranchRequest;
import com.stallworks.tako.core.common.dto.BranchResponse;

public interface BranchService {

	BranchResponse createBranch(BranchRequest request);
	List<BranchResponse> getAllBranch();
	List<BranchResponse> getAllBranchByActive(boolean active);

}
