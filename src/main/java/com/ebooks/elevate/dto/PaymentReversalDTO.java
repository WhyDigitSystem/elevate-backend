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
public class PaymentReversalDTO {

	private Long id;
	private String docId;
	private LocalDateTime docDate;
	private String originBill;
	private String origin;
	private String type;
	private String partyCode;
	private String partyName;
	private String gstState;
	private String gstIn;
	private BigDecimal paymentAmount;
	private String tdsAc;
	private LocalDateTime tdsAmount;
	private String paymentTime;
	private String bankCash;
	private String bankCharges;
	private String bankChargesAc;
	private String bcInCurrency;
	private BigDecimal sTaxAmount;
	private String taInCurrency;
	private String chequeBank;
	private LocalDateTime chqDt;
	private String payTo;
	private BigDecimal currency;
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

	List<PaymentInvoiceDTO> paymentInvoiceDTO;

	List<PaymentOtherAccountDTO> paymentOtherAccountDTO;

}
