package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arapoffsetinvoicedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArApOffSetInvoiceDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arapoffsetinvoicedetailsgen")
	@SequenceGenerator(name = "arapoffsetinvoicedetailsgen", sequenceName = "arapoffsetinvoicedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "arapoffsetinvoicedetailsid")
	private Long id;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "curr")
	private String curr;
	@Column(name = "exrate")
	private BigDecimal exRate;
	@Column(name = "invamount")
	private BigDecimal invAmount;
	@Column(name = "outstanding")
	private String outStanding;
	@Column(name = "settled")
	private String settled;
	@Column(name = "setexrate")
	private BigDecimal setExRate;
	@Column(name = "tnxsettled")
	private BigDecimal tnxSettled;
	@Column(name = "gainorloss")
	private BigDecimal gainOrLoss;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "arapadjustmentoffsetid")
	ArApAdjustmentOffSetVO arapadjustmentoffsetVO;
}
