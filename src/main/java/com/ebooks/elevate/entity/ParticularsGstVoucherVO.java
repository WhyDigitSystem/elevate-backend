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
@Table(name = "particularsgstvoucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticularsGstVoucherVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "particularsgstvouchergen")
	@SequenceGenerator(name = "particularsgstvouchergen", sequenceName = "particularsgstvoucherseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "particularsgstvoucherid")
	private Long id;
	@Column(name = "accountname")
	private String accountName;
	@Column(name = "subledgercode")
	private String subLedgerCode;
	@Column(name = "subledgername")
	private String subLedgerName;
	@Column(name = "debit")
	private BigDecimal debit;
	@Column(name = "basedbamount")
	private BigDecimal baseDbAmount;
	@Column(name = "credit")
	private BigDecimal credit;
	@Column(name = "basecramount")
	private BigDecimal baseCrAmount;
	@Column(name = "narration")
	private String narration;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "gstsalesvoucherid")
	GstSalesVoucherVO gstSalesVoucherVO;
}
