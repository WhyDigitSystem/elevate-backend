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
@Table(name = "costdebitnotegst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteGstVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costdebitnotegstgen")
	@SequenceGenerator(name = "costdebitnotegstgen", sequenceName = "costdebitnotegstseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costdebitnotegstid")
	private Long id;
	
	@Column(name = "chargeacc", length = 100)
	private String chargeAcc;

	@Column(name = "sublodgercode", length = 10)
	private String subLodgerCode;

	@Column(name = "dbillamt", precision = 10, scale = 2)
	private BigDecimal dBillAmt;

	@Column(name = "crbillamt", precision = 10, scale = 2)
	private BigDecimal crBillAmt;

	@Column(name = "dblcamt", precision = 10, scale = 2)
	private BigDecimal dBLCAmt;

	@Column(name = "crlcamt", precision = 10, scale = 2)
	private BigDecimal crLCAmt;

	@Column(name = "remarks", length = 150)
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="costdebitnoteid")
	@JsonBackReference
	private CostDebitNoteVO costDebitNoteVO;
	
}
