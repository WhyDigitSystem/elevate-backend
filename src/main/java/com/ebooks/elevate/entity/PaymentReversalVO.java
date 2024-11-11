package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paymentreversal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentReversalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentreversalgen")
	@SequenceGenerator(name = "paymentreversalgen", sequenceName = "paymentreversalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentreversalid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDateTime docDate;
	@Column(name = "originbill")
	private String originBill;
	@Column(name = "origin")
	private String origin;
	@Column(name = "type")
	private String type;
	@Column(name = "partycode")
	private String partyCode;
	@Column(name = "partyname")
	private String partyName;
	@Column(name = "gststate")
	private String gstState;
	@Column(name = "gstin")
	private String gstIn;
	@Column(name = "")
	private BigDecimal paymentAmount;
	@Column(name = "tdsac")
	private String tdsAc;
	@Column(name = "tdsamount")
	private LocalDateTime tdsAmount;
	@Column(name = "paymenttime")
	private String paymentTime;
	@Column(name = "bankcash")
	private String bankCash;
	@Column(name = "bankcharges")
	private String bankCharges;
	@Column(name = "bankchargerac")
	private String bankChargesAc;
	@Column(name = "bcincurrency")
	private String bcInCurrency;
	@Column(name = "staxamount")
	private BigDecimal sTaxAmount;
	@Column(name = "taincurrency")
	private String taInCurrency;
	@Column(name = "chequebank")
	private String chequeBank;
	@Column(name = "chqdt")
	private LocalDateTime chqDt;
	@Column(name = "payto")
	private String payTo;
	@Column(name = "currency")
	private BigDecimal currency;
	@Column(name = "offsetstatus")
	private String offSetStatus;
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
	@Column(name = "foxengainorloss")
	private BigDecimal foxenGainOrLoss;
	@Column(name = "roundoffamount")
	private BigDecimal roundOffAmount;
	@Column(name = "totalsettled")
	private BigDecimal totalSettled;
	@Column(name = "otheraccnetamt")
	private BigDecimal otherAccNetAmt;
	@Column(name = "onaccount")
	private BigDecimal onAccount;
	@Column(name = "narration")
	private String narration;

	@OneToMany(mappedBy = "paymentReversalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<PaymentInvoiceVO> paymentInvoiceVO;

	@OneToMany(mappedBy = "paymentReversalVO", cascade = CascadeType.ALL)
	@JsonBackReference
	List<PaymentOtherAccountVO> paymentOtherAccountVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}