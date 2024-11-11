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
@Table(name = "irncreditgst")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrnCreditGstVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "irncreditgstgen")
	@SequenceGenerator(name = "irncreditgstgen", sequenceName = "irncreditgstseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "irncreditgstid")
	private Long id;
	
	@Column(name = "chargeacc")
	private String chargeAcc;

	@Column(name = "sublodgercode")
	private String subLodgerCode;

	@Column(name = "dbillamt")
	private BigDecimal dBillAmt;

	@Column(name = "crbillamt")
	private BigDecimal crBillAmt;

	@Column(name = "dblcamt")
	private BigDecimal dBLCAmt;

	@Column(name = "crlcamt")
	private BigDecimal crLCAmt;

	@Column(name = "gstremarks")
	private String gstRemarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "irncreditid")
	IrnCreditVO irnCreditVO;

}
