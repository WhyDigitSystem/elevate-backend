package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brsexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrsExcelUploadVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brsexceluploadgen")
	@SequenceGenerator(name = "brsexceluploadgen", sequenceName = "brsexceluploadseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "brsexceluploadid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "billno")
	private String BillNo;

	@Column(name = "billdate")
	private LocalDate BillDate;

	@Column(name = "chqno")
	private String chqNo;

	@Column(name = "chqdate")
	private LocalDate chqDate;

	@Column(name = "bank")
	private String bank;

	@Column(name = "currency")
	private String currency;

	@Column(name = "exrate")
	private String exRate;

	@Column(name = "receiptamount")
	private BigDecimal receiptAmount;

	@Column(name = "paymentamount")
	private BigDecimal paymentAmount;

	@Column(name = "reconcile")
	private boolean reconcile;

	@Column(name = "customer")
	private String customer;

	@Column(name = "client")
	private String client;

	@Column(name = "branch")
	private String branch;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "finyear")
	private String finYear;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks")
	private String cancelRemarks;
}
