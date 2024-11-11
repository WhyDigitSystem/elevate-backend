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
@Table(name = "costdebitnotetaxprtcul")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteTaxPrtculVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costdebitNotetaxprtculgen")
	@SequenceGenerator(name = "costdebitNotetaxprtculgen", sequenceName = "costdebitNotetaxprtculseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costdebitnotetaxprtculid")
	private Long id;
	
	@Column(name = "tds", length = 25)
	private String tds;

	@Column(name = "tdspercentage", precision = 10, scale = 2)
	private BigDecimal tdsPercentage;

	@Column(name = "section", length = 25)
	private String section;

	@Column(name = "tottdsamt", precision = 10, scale = 2)
	private BigDecimal totTDSAmt;

	@ManyToOne
	@JoinColumn(name="costdebitnoteid")
	@JsonBackReference
	private CostDebitNoteVO costDebitNoteVO;
	
}
