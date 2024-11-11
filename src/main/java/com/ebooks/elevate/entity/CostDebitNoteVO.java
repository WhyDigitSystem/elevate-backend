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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costdebitnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costdebitnotegen")
	@SequenceGenerator(name = "costdebitnotegen", sequenceName = "costdebitnoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costdebitnoteid")
	private Long id;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 25)
	private String branchCode;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "updatedby", length = 25)
	private String modifyBy;
	private boolean active;
	private boolean cancel;
	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "screencode", length = 10)
	private String screenCode="CDN";
	@Column(name = "screenname", length = 25)
	private String screenName="COST DEBIT NOTE";
	@Column(name = "ipno", length = 15)
	private String ipNo;
	@Column(name = "latitude", length = 100)
	private String latitude;
	@Column(name = "orgid", length = 15)
	private Long orgId;
	@Column(name = "docno", length = 50)
	private String docNo;
	
	@Column(name = "docid", length = 50)
	private String docId;

	@Column(name = "subtype", length = 25)
	private String subType;

	@Column(name = "product", length = 50)
	private String product;
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();

	@Column(name = "vohno", length = 50)
	private String vohNo;

	@Column(name = "vohdate", length = 50)
	private LocalDate vohDate; // This can be changed to Date if needed

	@Column(name = "partytype", length = 50)
	private String partyType;

	@Column(name = "supprefno", length = 50)
	private String suppRefNo;
	@Column(name = "suppdate")
	private LocalDate suppDate;

	@Column(name = "partyname", length = 150)
	private String partyName;

	@Column(name = "partycode", length = 50)
	private String partyCode;

	@Column(name = "creditdays")
	private int creditDays;
	@Column(name = "duedate")
	private LocalDate dueDate;

	@Column(name = "taxexampt")
	private boolean taxExampt;

	@Column(name = "address", length = 150)
	private String address;

	@Column(name = "currency", length = 10)
	private String currency;

	@Column(name = "exrate", precision = 10, scale = 2)
	private BigDecimal exRate;

	@Column(name = "otherinfo", length = 150)
	private String otherInfo;

	@Column(name = "remarks", length = 150)
	private String remarks;

	@Column(name = "shiprefno", length = 50)
	private String shipRefNo;

	@Column(name = "status", length = 10)
	private String status;

	@Column(name = "orginbill", length = 50)
	private String orginBill;

	@Column(name = "gsttype", length = 10)
	private String gstType;
	
	@Column(name = "currentdate")
	private LocalDate currentDate;
	
	@Column(name = "currentdatevalue")
	private BigDecimal currentDateValue;
	
	@Column(name = "partyaddtype",length =50)
	private String partyAddType;
	
	@OneToMany(mappedBy ="costDebitNoteVO",cascade = CascadeType.ALL)
	@JsonManagedReference
    List <CostDebitNoteSummaryVO> costDebitNoteSummaryVO;
	
	@OneToMany(mappedBy ="costDebitNoteVO",cascade = CascadeType.ALL)
	@JsonManagedReference
    List <CostDebitChargesVO> costDebitChargesVO;
	
	@OneToMany(mappedBy ="costDebitNoteVO",cascade = CascadeType.ALL)
	@JsonManagedReference
    List <CostDebitNoteGstVO> costDebitNoteGstVO;
	
	@OneToMany(mappedBy ="costDebitNoteVO",cascade = CascadeType.ALL)
	@JsonManagedReference
    List <CostDebitNoteTaxPrtculVO> costDebitNoteTaxPrtculVO;
	
	
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
