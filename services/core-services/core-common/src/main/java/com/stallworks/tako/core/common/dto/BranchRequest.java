package com.stallworks.tako.core.common.dto;

import jakarta.validation.constraints.NotNull;

public record BranchRequest(
		@NotNull String branchName,
		@NotNull boolean active
		
		) {

}
