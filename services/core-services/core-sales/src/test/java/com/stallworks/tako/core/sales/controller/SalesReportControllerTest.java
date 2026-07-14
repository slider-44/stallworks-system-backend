package com.stallworks.tako.core.sales.controller;

import com.stallworks.tako.core.sales.TestApplication;
import com.stallworks.tako.core.sales.dto.SalesLineItemRequest;
import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;
import com.stallworks.tako.core.sales.service.SalesReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalesReportController.class)
@ContextConfiguration(classes = TestApplication.class) 
class SalesReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesReportService salesReportService;

    @Test
    void create_shouldReturnOkWithSalesReport() throws Exception {
        // Given
        SalesReportResponse response = new SalesReportResponse(
                100L, 
                1L, 
                2L, 
                LocalDate.of(2026, 7, 12),
                LocalTime.of(8, 0), 
                LocalTime.of(17, 0),
                List.of(), 
                new BigDecimal("245.00"), 
                null
        );

        when(salesReportService.create(any(SalesReportRequest.class))).thenReturn(response);

        String requestJson = """
                {
                    "branchId": 1,
                    "employeeId": 2,
                    "date": "2026-07-12",
                    "timeIn": "08:00",
                    "timeOut": "17:00",
                    "lineItems": [
                        {
                            "containerSize": "SOLO",
                            "quantitySold": 5
                        }
                    ]
                }
                """;

        // When & Then
        mockMvc.perform(post("/v1/sales-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())           // Note: you're using .ok(), not .created()
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.totalSales").value(245.00))
                .andExpect(jsonPath("$.branchId").value(1));
    }

    @Test
    void getAll_shouldReturnListOfReports() throws Exception {
        SalesReportResponse report1 = new SalesReportResponse(1L, 1L, 2L, LocalDate.now(), 
                LocalTime.of(8,0), LocalTime.of(17,0), List.of(), new BigDecimal("100"), null);

        when(salesReportService.findAll()).thenReturn(List.of(report1));

        mockMvc.perform(get("/v1/sales-reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}