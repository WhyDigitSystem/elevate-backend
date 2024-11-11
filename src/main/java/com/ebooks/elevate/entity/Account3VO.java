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
@Table(name = "account3")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account3VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "account3gen")
	@SequenceGenerator(name = "account3gen", sequenceName = "account3seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "account3id")
	private Long id;
	@Column(name = "company")
	private String company;
	@Column(name = "branchlocation")
	private String branchLocation;

	@ManyToOne
	@JoinColumn(name = "accountid")
	@JsonBackReference
	private AccountVO accountVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
