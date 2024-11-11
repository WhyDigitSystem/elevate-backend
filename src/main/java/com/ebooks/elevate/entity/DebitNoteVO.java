package com.ebooks.elevate.entity;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "debitnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebitNoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debitnotegen")
	@SequenceGenerator(name = "debitnotegen", sequenceName = "debitnoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "debitnoteid")
	private Long id;
	@Column(name = "docno")
	private String docNo;
	@Column(name = "subtype")
	private String subType;
	@Column(name = "product")
	private String product;
	@Column(name = "docdate")
	private LocalDateTime docDate;
	@Column(name = "partyType")
	private String partyType;
	@Column(name = "partycode")
	private String partyCode;
	@Column(name = "partyname")
	private String partyName;
	@Column(name = "address")
	private String address;
	@Column(name = "otherinfo")
	private String otherInfo;
	@Column(name = "status")
	private String status;
	@Column(name = "originBill")
	private String originBill;
	@Column(name = "vchno")
	private String vchNo;
	@Column(name = "vchdate")
	private LocalDateTime vchDate;
	@Column(name = "creditdays")
	private String creditDays;
	@Column(name = "supplierrefno")
	private String supplierRefNo;
	@Column(name = "date")
	private String date;	
	@Column(name = "duedate")
	private String dueDate;
	@Column(name = "exrate")
	private String exRate;
	@Column(name = "taxexempt")
	private boolean taxExempt;
	@Column(name = "currency")
	private String currency;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "shipperrefno")
	private String shipperRefNo;
	@Column(name = "gstType")
	private String gstType;
	@Column(name = "mode")
	private String mode;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "billcurrtotchargeamount")
	private String billCurrTotChargeAmount;
	@Column(name = "billcurrtotgrossamount")
	private String billCurrTotGrossAmount;
	@Column(name = "billcurrnetamount")
	private String billCurrNetAmount;
	@Column(name = "amountinwords")
	private String amountInWords;
	@Column(name = "roundoff")
	private String roundOff;
	@Column(name = "lctotchargeamount")
	private String lctotChargeAmount;
	@Column(name = "lctotgrossamount")
	private String lctotGrossAmount;
	@Column(name = "lcnetamount")
	private String lcNetAmount;
	
	@OneToMany(mappedBy = "debitNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ChargerDebitNoteVO> chargerDebitNoteVO;

	@OneToMany(mappedBy = "debitNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ParticularsDebitNoteVO> particularsDebitNoteVO;

	@OneToMany(mappedBy = "debitNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<GstDebitNoteVO> gstDebitNoteVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
