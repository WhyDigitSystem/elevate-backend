package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brsopening")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrsOpeningVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brsopeninggen")
	@SequenceGenerator(name = "brsopeninggen", sequenceName = "brsopeningseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "brsopeningid")
	private Long id;
	@Column(name = "billno",length = 30)
	private String billNo;
	@Column(name = "billdate")
	private LocalDate billDate;
	@Column(name = "chqno")
	private String chqNo;
	@Column(name = "chqdate")
	private LocalDate chqDate;
	@Column(name = "bank")
	private String bank;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exrate")
	private BigDecimal exRate;
	@Column(name = "receiptamount")
	private BigDecimal receiptAmount;
	@Column(name = "paymentamount")
	private BigDecimal paymentAmount;
	@Column(name = "reconcile")
	private boolean reconcile;

//	Common Fields

	@Column(name = "orgid")
	private Long orgId;

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
	private String screenCode = "ARBB";

	@Column(name = "screenname", length = 25)
	private String screenName = "AR BILL BALANCE";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
