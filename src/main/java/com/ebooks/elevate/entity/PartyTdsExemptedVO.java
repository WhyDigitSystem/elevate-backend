package com.ebooks.elevate.entity;

import java.math.BigDecimal;

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
@Table(name = "partytdsexempted")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyTdsExemptedVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partytdsexemptedgen")
	@SequenceGenerator(name = "partytdsexemptedgen", sequenceName = "partytdsexemptedseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partytdsexemptedid")
	private Long id;
	@Column(name = "tds_exemp_certi", length = 150)
	private String tdsExempCerti;

	@Column(name = "value", precision = 10, scale = 2)
	private BigDecimal value;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}