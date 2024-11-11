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
@Table(name = "settaxrate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetTaxRateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "settaxrategen")
	@SequenceGenerator(name = "settaxrategen", sequenceName = "settaxrateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "settaxrateid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "chapter")
	private String chapter;
	@Column(name = "subchapter")
	private String subChapter;
	@Column(name = "hsncode")
	private String hsnCode;
	@Column(name = "branchlocation")
	private String branch;
	@Column(name = "newrate")
	private float newRate;
	@Column(name = "excepmted")
	private String excepmted;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

}
