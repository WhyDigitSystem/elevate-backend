package com.ebooks.elevate.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partyvendorevaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyVendorEvaluationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyvendorevaluationgen")
	@SequenceGenerator(name = "partyvendorevaluationgen", sequenceName = "partyvendorevaluationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partyvendorevaluationid")
	private Long id;
	
	@Column(name = "boughvendor", length = 150)
	private String boughVendor;

	@Column(name = "basicvenselected", length = 50)
	private String basicVenSelected;

	@Column(name = "justification", length = 50)
	private String justification;

	@Column(name = "slapoints", length = 50)
	private String slaPoints;

	@Column(name = "commagreedterm", length = 50)
	private String commAgreedTerm;

	@OneToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
