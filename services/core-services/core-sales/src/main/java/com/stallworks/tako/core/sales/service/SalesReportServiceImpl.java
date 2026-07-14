package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.inventory.entity.Containers;
import com.stallworks.tako.core.inventory.repository.ContainerRepository;
import com.stallworks.tako.core.sales.dto.SalesLineItemRequest;
import com.stallworks.tako.core.sales.dto.SalesReportMapper;
import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;
import com.stallworks.tako.core.sales.entity.SalesLineItem;
import com.stallworks.tako.core.sales.entity.SalesReport;
import com.stallworks.tako.core.sales.enums.ContainerSize;
import com.stallworks.tako.core.sales.repository.SalesReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

	private final SalesReportRepository salesReportRepository;

	private final ContainerRepository containerRepository;

	private final SalesReportMapper salesReportMapper;

	@Override
	public SalesReportResponse create(SalesReportRequest request) {

		List<SalesLineItem> lineItems = request.lineItems().stream().map(this::toLineItem).toList();

		BigDecimal total = lineItems.stream().map(SalesLineItem::getLineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

		SalesReport report = SalesReport.builder().employeeId(request.employeeId()).branchId(request.branchId())
				.date(request.date()).timeIn(request.timeIn()).timeOut(request.timeOut()).totalSales(total).build();

		lineItems.forEach(li -> li.setSalesReport(report));
		report.setLineItems(lineItems);

		SalesReport saved = salesReportRepository.save(report);
		return salesReportMapper.toResponse(saved);

	}

	private SalesLineItem toLineItem(SalesLineItemRequest item) {
		BigDecimal unitPrice;

		if (item.containerSize() == ContainerSize.ADD_ONS) {
			if (item.manualUnitPrice() == null || item.manualUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("Add Ons requires a manual unit price greater than 0");
			}
			unitPrice = item.manualUnitPrice();
		} else {
			Containers container = containerRepository.findByContainerSize(item.containerSize().name())
					.orElseThrow(() -> new IllegalArgumentException("Unknown container size: " + item.containerSize()));
			unitPrice = container.getPrice();
		}

		BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.quantitySold()));

		return SalesLineItem.builder().containerSize(item.containerSize()).unitPrice(unitPrice)
				.quantitySold(item.quantitySold()).lineTotal(lineTotal).build();

	}
	
	   @Override
	   public List<SalesReportResponse> findAll() {
		
		   return salesReportRepository.findAll().stream()
				   .map(salesReportMapper:: toResponse)
				   .toList();
		   
	   }
	   

}
