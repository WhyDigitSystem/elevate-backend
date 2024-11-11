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
@Table(name = "irncreditcharges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrnCreditChargesVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "irncreditchargesgen")
	@SequenceGenerator(name = "irncreditchargesgen", sequenceName = "irncreditchargesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "irncreditchargesid")
	private Long id;
	
	@Column(name = "jobno")
	private String jobNo;

	@Column(name = "houseno")
	private String houseNo;

	@Column(name = "chargecode")
	private String chargeCode;

	@Column(name = "gchargecode")
	private String gchargeCode;

	@Column(name = "chargename")
	private String chargeName;

	@Column(name = "applyon")
	private String applyOn;

	@Column(name = "currency")
	private String currency;

	@Column(name = "exrate")
	private BigDecimal exRate;

	@Column(name = "rate")
	private BigDecimal rate;

	@Column(name = "exampted")
	private boolean exampted;

	@Column(name = "fcamt")
	private BigDecimal fcAmt;

	@Column(name = "lcamt")
	private BigDecimal lcAmt;

	@Column(name = "tlcamt")
	private BigDecimal tlcAmt;

	@Column(name = "billamt")
	private BigDecimal billAmt;

	@Column(name = "gstpercentage")
	private int gstPercentage;

	@Column(name = "gst")
	private BigDecimal gst;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "irncreditid")
	IrnCreditVO irnCreditVO;
}
