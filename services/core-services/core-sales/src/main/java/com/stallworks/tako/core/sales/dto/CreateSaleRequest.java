package com.stallworks.tako.core.sales.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateSaleRequest(
		@NotBlank String branchId,
        @NotBlank String crewId,
        @NotNull  LocalDate saleDate,
        @NotNull  LocalTime timeIn,
        @NotNull  LocalTime timeOut,
        @NotEmpty @Valid List<SaleLineRequest> lineItems) {

}
