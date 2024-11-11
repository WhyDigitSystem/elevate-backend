package com.ebooks.elevate.entity;

import java.math.BigDecimal;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costdebitnotesummary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteSummaryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costdebitnotesummarygen")
	@SequenceGenerator(name = "costdebitnotesummarygen", sequenceName = "costdebitnotesummaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costdebitnotesummaryid")
	private Long id;
	
	@Column(name = "totchargesbillcurramt", precision = 10, scale = 2)
	private BigDecimal totChargesBillCurrAmt;

	@Column(name = "totchargeslcamp", precision = 10, scale = 2)
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
	
	@Column(name = "roundoff",precision =10,scale =2)
	private BigDecimal roundOff;
	
	
	@ManyToOne
	@JoinColumn(name="costdebitnoteid")
	@JsonBackReference
	private CostDebitNoteVO costDebitNoteVO;
	
}
