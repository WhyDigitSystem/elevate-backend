package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptReversalDTO {
	private Long id;
	private String docId;
	private LocalDateTime docDate;
	private String originBill;
	private LocalDateTime originDate;
	private String type;
	private String receiptType;
	private String customerName;
	private String customerCode;
	private String bankCash;
	private String receiptAmount;
	private String currency;
	private String bankChargesAc;
	private String bankCharges;
	private String bcInCurrency;
	private String tdsAmount;
	private String tdsInCurrency;
	private String chequeBank;
	private String receiptType2;
	private String chqNo;
	private String chqDt;
	private String receivedFrom;
	private String offSetStatus;
	private Long orgId;
	private boolean active;
	private String createdBy;
	private BigDecimal foxenGainOrLoss;
	private BigDecimal roundOffAmount;
	private BigDecimal totalSettled;
	private BigDecimal otherAccNetAmt;
	private BigDecimal onAccount;
	private String narration;

	List<ReceiptInvoiceDTO> receiptInvoiceDTO;
	
	List<ReceiptOtherAccountDTO> receiptOtherAccountDTO;

}
