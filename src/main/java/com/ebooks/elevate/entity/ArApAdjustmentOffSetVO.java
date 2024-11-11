package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arapadjustmentoffset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArApAdjustmentOffSetVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adjustmentoffsetgen")
	@SequenceGenerator(name = "adjustmentoffsetgen", sequenceName = "adjustmentoffsetseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "adjustmentoffsetid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "subledgertype")
	private String subLedgerType;
	@Column(name = "subledgername")
	private String subLedgerName;
	@Column(name = "subledgercode")
	private String subLedgerCode;
	@Column(name = "receiptpaymentdocid")
	private String receiptPaymentDocId;
	@Column(name = "receiptpaymentdocdate")
	private LocalDate receiptPaymentDocDate;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exrate")
	private BigDecimal exRate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "supplierRefNo")
	private String supplierRefNo;
	@Column(name = "forexgainorloss")
	private BigDecimal forexGainOrLoss;
	@Column(name = "totalsettled")
	private BigDecimal totalSettled;
	@Column(name = "roundoffamount")
	private BigDecimal roundOffAmount;
	@Column(name = "onaccount")
	private BigDecimal onAccount;
	@Column(name = "narration")
	private String narration;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	
	@OneToMany(mappedBy = "arapadjustmentoffsetVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ArApOffSetInvoiceDetailsVO> arApAdjustmentInvoiceDetailsVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
