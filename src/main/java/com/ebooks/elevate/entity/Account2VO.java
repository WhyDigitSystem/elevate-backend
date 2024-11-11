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
@Table(name = "account2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account2VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "account2gen")
	@SequenceGenerator(name = "account2gen", sequenceName = "account2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "account2id")
	private Long id;
	@Column(name = "banktype")
	private String bankType;
	@Column(name = "accountno")
	private Long accountNo;
	@Column(name = "overdraftlimit")
	private Long overDraftLimit;

	@ManyToOne
	@JoinColumn(name = "accountid")
	@JsonBackReference
	private AccountVO accountVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
