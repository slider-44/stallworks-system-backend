package com.stallworks.tako.core.sales.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stallworks.tako.core.inventory.entity.Containers;
import com.stallworks.tako.core.inventory.repository.ContainerRepository;
import com.stallworks.tako.core.sales.dto.SalesLineItemRequest;
import com.stallworks.tako.core.sales.dto.SalesReportMapper;
import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;
import com.stallworks.tako.core.sales.entity.SalesReport;
import com.stallworks.tako.core.sales.enums.ContainerSize;
import com.stallworks.tako.core.sales.repository.SalesReportRepository;


@ExtendWith(MockitoExtension.class)
public class SalesReportServiceImplTest {
	
	@Mock
	private SalesReportRepository  salesReportRepository ;
	
	@Mock
	private ContainerRepository containerRepository;
	
	@Mock
	private SalesReportMapper salesReportMapper;
	
	@InjectMocks
	private SalesReportServiceImpl service;
	
	@Test
	void create_computesCorrectTotal_forKnownContainerSize() {
		
		 Containers solo = Containers.builder()
	                .containerSize("SOLO")
	                .price(new BigDecimal("49.00"))
	                .build();
		 
	      when(containerRepository.findByContainerSize("SOLO"))
	      .thenReturn(Optional.of(solo));
	      
	      SalesReportRequest request = new SalesReportRequest(
	              1L, 1L, 2L, LocalDate.of(2026, 7, 12), LocalTime.of(8, 0), LocalTime.of(17, 0),
	              List.of(new SalesLineItemRequest(ContainerSize.SOLO, 5, null))
	      );
	      
	      when(salesReportRepository.save(any(SalesReport.class)))
          .thenAnswer(inv -> inv.getArgument(0));
	      
	      when(salesReportMapper.toResponse(any(SalesReport.class)))
          .thenAnswer(inv -> {
              SalesReport r = inv.getArgument(0);
              return new SalesReportResponse(
                      r.getId(), r.getBranchId(), r.getEmployeeId(),
                      r.getDate(), r.getTimeIn(), r.getTimeOut(),
                      List.of(), r.getTotalSales(),
                      r.getCreatedAt(), r.getCreatedBy(), r.getUpdatedBy()
              );
          });
	      
	   // When
	      SalesReportResponse response = service.create(request);
	      
	   // Then
	      assertThat(response.totalSales()).isEqualByComparingTo("245.00");

	      verify(containerRepository).findByContainerSize("SOLO");
	      verify(salesReportRepository).save(any(SalesReport.class));  
		
	}
	
	@Test
	void create_rejectsAddOns_whenNoManualPriceGiven() {
		
	    SalesReportRequest request = new SalesReportRequest(
	            1L, 1L, 2L, LocalDate.of(2026, 7, 12), LocalTime.of(8, 0), LocalTime.of(17, 0),
	            List.of(new SalesLineItemRequest(ContainerSize.ADD_ONS, 2, new BigDecimal("30.00")))
	    );
		  assertThatThrownBy(() -> service.create(request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Add Ons requires a manual unit price");
	}
	
	@Test
	void create_acceptsAddOns_whenManualPriceGiven() {
	    SalesReportRequest request = new SalesReportRequest(
	            1L, 1L, 2L, LocalDate.of(2026, 7, 12), LocalTime.of(8, 0), LocalTime.of(17, 0),
	            List.of(new SalesLineItemRequest(ContainerSize.ADD_ONS, 2, new BigDecimal("30.00")))
	    );
	    when(salesReportRepository.save(any(SalesReport.class))).thenAnswer(inv -> inv.getArgument(0));
	    when(salesReportMapper.toResponse(any(SalesReport.class))).thenAnswer(inv -> {
	        SalesReport r = inv.getArgument(0);
	        return new SalesReportResponse(r.getId(), r.getBranchId(), r.getEmployeeId(),
	                r.getDate(), r.getTimeIn(), r.getTimeOut(), List.of(), r.getTotalSales(),
	                r.getCreatedAt(), r.getCreatedBy(), r.getUpdatedBy() );
	    });

	    SalesReportResponse response = service.create(request);

	    assertThat(response.totalSales()).isEqualByComparingTo("60.00"); // 30.00 × 2
	}

}
