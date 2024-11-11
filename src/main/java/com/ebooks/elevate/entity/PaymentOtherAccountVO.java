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
@Table(name = "paymentotheraccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOtherAccountVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentotheraccountgen")
	@SequenceGenerator(name = "paymentotheraccountgen", sequenceName = "paymentotheraccountseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentotheraccountid")
	private Long id;
	@Column(name = "accountName")
	private String accountName;
	@Column(name = "subledgername")
	private String subLedgerName;
	@Column(name = "debitamount")
	private BigDecimal debitAmount;
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "paymentreversalid")
	PaymentReversalVO paymentReversalVO;
}
