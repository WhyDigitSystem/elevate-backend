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
@Table(name = "partyspecialtds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartySpecialTDSVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyspecialtdsgen")
	@SequenceGenerator(name = "partyspecialtdsgen", sequenceName = "partyspecialtdsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partyspecialtdsid")
	private Long id;
	
	@Column(name = "tdswithsec", length = 150)
	private String tdsWithSec;

	@Column(name = "ratefrom")
	private Long rateFrom;

	@Column(name = "rateto")
	private Long rateTo;

	@Column(name = "tdswithper")
	private Long tdsWithPer;

	@Column(name = "surchargeper")
	private Long surchargePer;

	@Column(name = "edpercentage")
	private Long edPercentage;

	@Column(name = "tdscertifino", length = 50)
	private String tdsCertifiNo;

	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
