package com.stallworks.tako.core.common.dto;

import java.math.BigDecimal;
import java.util.List;


public record EmployeeResponse( 
	Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        Role role,
        boolean active,
        List<Long> branchIds,
        BigDecimal hourlyRate) {

}
