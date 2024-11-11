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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "irncreditnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrnCreditVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "irncreditgen")
	@SequenceGenerator(name = "irncreditgen", sequenceName = "irncreditseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "irncreditid")
	private Long id;

	// Common fields
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@Column(name = "screencode", length = 5)
	@Builder.Default
	private String screenCode = "IRN CREDIT NOTE";

	@Column(name = "screenname", length = 25)
	@Builder.Default
	private String screenName = "ICN";

	@Column(name = "orgid")
	private Long orgId;

	// IRN fields
	@Column(name = "docid", length = 50)
	private String docId;

	@Column(name = "docdate")
	@Builder.Default
	private LocalDate docDate = LocalDate.now();

	@Column(name = "vohno", length = 50)
	private String vohNo;

	@Column(name = "vohdate")
	private LocalDate vohDate;

	@Column(name = "partyname", length = 50)
	private String partyName;

	@Column(name = "partycode", length = 25)
	private String partyCode;

	@Column(name = "suprefno", length = 50)
	private String supRefNo;

	@Column(name = "suprefdate")
	private LocalDate supRefDate;

	@Column(name = "partytype", length = 15)
	private String partyType;

	@Column(name = "product", length = 25)
	private String product;

	@Column(name = "creditdays", length = 5)
	private int creditDays;

	@Column(name = "duedate")
	private LocalDate dueDate;

	@Column(name = "state", length = 25)
	private String state;

	@Column(name = "city", length = 25)
	private String city;

	@Column(name = "officetype", length = 15)
	private String officeType;

	@Column(name = "currency", length = 25)
	private String currency;

	@Column(name = "exrate", precision = 10, scale = 2)
	private BigDecimal exRate;
	
	@Column(name = "summaryexrate", precision = 10, scale = 2)
	private BigDecimal summaryExRate;
	
	@Column(name="currentdate")
	private LocalDate currentDate;
	
	@Column(name="currentdatevalue")
	private Long currentDateValue;
	
	@Column(name="roundoff")
	private Long roundOff;

	@Column(name = "status", length = 10)
	private String status;

	@Column(name = "orginbill", length = 50)
	private String originBill;

	@Column(name = "remarks", length = 150)
	private String remarks;

	@Column(name = "address", length = 150)
	private String address;

	@Column(name = "shiprefno", length = 50)
	private String shipRefNo;

	@Column(name = "pincode", length = 10)
	private int pincode;

	@Column(name = "gstin", length = 50)
	private String gstin;

	@Column(name = "gsttype", length = 20)
	private String gstType;

	@Column(name = "billingmonth", length = 15)
	private String billingMonth;

	@Column(name = "otherinfo", length = 150)
	private String otherInfo;

	@Column(name = "salestype", length = 20)
	private String salesType;

	@Column(name = "creditremarks", length = 150)
	private String creditRemarks;

	@Column(name = "charges", length = 50)
	private String charges;

	// Summary Fields
	@Column(name = "totchargesbillcurramt", precision = 10, scale = 2)
	private BigDecimal totChargesBillCurrAmt;

	@Column(name = "totchargeslcamt", precision = 10, scale = 2)
	private BigDecimal totChargesLCAmt;

	@Column(name = "totgrossbillamt", precision = 10, scale = 2)
	private BigDecimal totGrossBillAmt;

	@Column(name = "totgrosslcamt", precision = 10, scale = 2)
	private BigDecimal totGrossLCAmt;

	@Column(name = "netbillcurramt", precision = 10, scale = 2)
	private BigDecimal netBillCurrAmt;

	@Column(name = "netlcamt", precision = 10, scale = 2)
	private BigDecimal netLCAmt;

	@Column(name = "amtinwords", length = 150)
	private String amtInWords;

	@Column(name = "tottaxamt", precision = 10, scale = 2)
	private BigDecimal totTaxAmt;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@OneToMany(mappedBy = "irnCreditVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IrnCreditChargesVO> irnCreditChargesVO;

	@OneToMany(mappedBy = "irnCreditVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IrnCreditGstVO> irnCreditGstVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
