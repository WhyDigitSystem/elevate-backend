package com.ebooks.elevate.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account1VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "account1gen")
	@SequenceGenerator(name = "account1gen", sequenceName = "account1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "account1id")
	private Long id;

	@Column(name = "balancesheet")
	private String balanceSheet;
	@Column(name = "cashflowstatement")
	private String cashFlowStatement;
	@Column(name = "incomestatement")
	private String incomeStatement;

	@ManyToOne
	@JoinColumn(name = "accountid")
	@JsonBackReference
	private AccountVO accountVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
