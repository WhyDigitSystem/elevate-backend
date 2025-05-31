package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookingDTO {
	
	private Long orgId;
	private String client;
	private String clientCode;
	private String year;
	private String month;
	private String type; 
	private BigDecimal amount;
	private String segment;
	private String customerName;
	private BigDecimal orderValue;
	private BigDecimal existingOrderPlan;
	private BigDecimal balanceOrderValue;
	private BigDecimal paymentReceived;
	private BigDecimal yetToReceive;
	private String classification;
	private String createdBy;
	private String yearType;

}
