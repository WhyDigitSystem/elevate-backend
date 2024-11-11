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
@Table(name = "partychargesexemption")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyChargesExemptionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partychargesexemptiongen")
	@SequenceGenerator(name = "partychargesexemptiongen", sequenceName = "partychargesexemptionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partychargesexemptionid")
	private Long id;
	
	@Column(name = "tdswithsec", length = 15)
	private String tdsWithSec;

	@Column(name = "charges", length = 15)
	private String charges;

	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
