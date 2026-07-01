package com.stallworks.tako.user.dto;

import lombok.Builder;

@Builder
public record EmployeeSummary(  
		Long id,
        String firstName,
        String lastName,
        Role role) {

}
