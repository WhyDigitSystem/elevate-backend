package com.ebooks.elevate.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subledgeraccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubLedgerAccountVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subledgeraccountgen")
	@SequenceGenerator(name = "subledgeraccountgen", sequenceName = "subledgeraccountseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subledgeraccountid")
	private Long id;
	
	@Column(name = "accountscategory")
	private String accountsCategory; 
	@Column(name = "subledgertype")
	private String subLedgerType;
	@Column(name = "newcode")
	private String newCode;
	@Column(name = "oldcode")
	private String oldCode;
	@Column(name = "subledgername")
	private String subLedgerName;
	@Column(name = "controllAccount")
	private String controllAccount;
	@Column(name = "currency")
	private String currency;
	@Column(name = "creditdays")
	private String creditDays;
	@Column(name = "creditLimit")
	private String creditLimit;
	@Column(name = "vatno")
	private String vatno;
	@Column(name = "statejurisiction")
	private String stateJutisiction;
	@Column(name = "invoiceType")
	private String invoiceType;
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
