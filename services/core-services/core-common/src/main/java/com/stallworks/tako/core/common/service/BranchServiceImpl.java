package com.stallworks.tako.core.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.common.dto.BranchRequest;
import com.stallworks.tako.core.common.dto.BranchResponse;
import com.stallworks.tako.core.common.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
	
	private final BranchRepository branchRepository;

	@Override
	public BranchResponse createBranch(BranchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BranchResponse> getAllBranch() {

		
		return branchRepository.findAll().stream()
				.map(branch -> new BranchResponse(
						branch.getId(),
						branch.getBranchName(),
						branch.isActive()
				))
				.toList();
						
	}

	@Override
	public List<BranchResponse> getAllBranchByActive(boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

}
