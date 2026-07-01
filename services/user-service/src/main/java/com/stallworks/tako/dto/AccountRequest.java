package com.stallworks.tako.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AccountRequest (
		@NotNull Long employeeId,
		@NotBlank String userName,
        @NotBlank @Size(min = 8) String password
        ) 
{}
