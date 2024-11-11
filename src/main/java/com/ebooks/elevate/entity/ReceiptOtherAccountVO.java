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
@Table(name = "receiptotheraccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptOtherAccountVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiptotheraccountgen")
	@SequenceGenerator(name = "receiptotheraccountgen", sequenceName = "receiptotheraccountseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "receiptotheraccountid")
	private Long id;
	@Column(name = "accountname")
	private String accountName;
	@Column(name = "subledgername")
	private String subLedgerName;
	@Column(name = "credit")
	private String credit;
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "receiptreversalid")
	ReceiptReversalVO receiptReversalVO;
}
