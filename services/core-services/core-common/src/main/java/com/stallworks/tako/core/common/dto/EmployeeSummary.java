package com.stallworks.tako.core.common.dto;

import lombok.Builder;

@Builder
public record EmployeeSummary(  
		Long id,
        String firstName,
        String lastName,
        Role role) {

}
