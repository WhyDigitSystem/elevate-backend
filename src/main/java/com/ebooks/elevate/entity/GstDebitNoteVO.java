package com.ebooks.elevate.entity;

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
@Table(name = "gstdebitnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstDebitNoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gstdebitnotegen")
	@SequenceGenerator(name = "gstdebitnotegen", sequenceName = "gstdebitnoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gstdebitnoteid")
	private Long id;
	@Column(name = "chargeaccount")
	private String chargeAccount;
	@Column(name = "subledgercode")
	private String subLedgerCode;
	@Column(name = "dbbillamount")
	private String dbBillAmount;
	@Column(name = "crbillamount")
	private String crBillAmount;
	@Column(name = "dblcamt")
	private String dblcAmt;
	@Column(name = "crlcamt")
	private String crlcamt;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "debitamountVO")
	DebitNoteVO debitNoteVO;




	
}
