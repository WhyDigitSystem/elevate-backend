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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costinvsummary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostInvSummaryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costinvsummarygen")
	@SequenceGenerator(name = "costinvsummarygen", sequenceName = "costinvsummaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costinvsummaryid")
	private Long id;
	@Column(name ="totchargesbillcurramt",precision =10,scale =2)
	private BigDecimal totChargesBillCurrAmt;
	@Column(name ="totchargeslcamt",precision =10,scale =2)
	private BigDecimal totChargesLcAmt;
	@Column(name ="actbillcurramt",precision =10,scale =2)
	private BigDecimal actBillCurrAmt;
	@Column(name ="actbilllcamt",precision =10,scale =2)
	private BigDecimal actBillLcAmt;
	@Column(name ="netbillcurramt",precision =10,scale =2)
	private BigDecimal netBillCurrAmt;
	@Column(name ="netbilllcamt",precision =10,scale =2)
	private BigDecimal netBillLcAmt;
	@Column(name ="roundoff",precision =10,scale =2)
	private BigDecimal roundOff;
	@Column(name ="gstinputlcamt",precision =10,scale =2)
	private BigDecimal gstInputLcAmt;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "costinvoiceid")
	CostInvoiceVO costInvoiceVO;
}
