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
@Table(name = "receiptreversal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptReversalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiptreversalgen")
	@SequenceGenerator(name = "receiptreversalgen", sequenceName = "receiptreversalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "receiptreversalid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDateTime docDate;
	@Column(name = "originbill")
	private String originBill;
	@Column(name = "origindate")
	private LocalDateTime originDate;
	@Column(name = "type")
	private String type;
	@Column(name = "receipttype")
	private String receiptType;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "bankCash")
	private String bankCash;
	@Column(name = "receiptAmount")
	private String receiptAmount;
	@Column(name = "currency")
	private String currency;
	@Column(name = "bankchargesac")
	private String bankChargesAc;
	@Column(name = "bankcharges")
	private String bankCharges;
	@Column(name = "bcincurrency")
	private String bcInCurrency;
	@Column(name = "tdsamount")
	private String tdsAmount;
	@Column(name = "tdsincurrency")
	private String tdsInCurrency;
	@Column(name = "chequeBank")
	private String chequeBank;
	@Column(name = "receipttype2")
	private String receiptType2;
	@Column(name = "chqno")
	private String chqNo;
	@Column(name = "chqdt")
	private String chqDt;
	@Column(name = "receivedfrom")
	private String receivedFrom;
	@Column(name = "offSetStatus")
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

	@OneToMany(mappedBy = "receiptReversalVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ReceiptInvoiceVO> receiptInvoiceVO;
	
	@OneToMany(mappedBy = "receiptReversalVO",cascade = CascadeType.ALL)
	@JsonBackReference
	List<ReceiptOtherAccountVO> receiptOtherAccountVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
