package com.ebooks.elevate.entity;

import java.time.LocalDate;

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
@Table(name = "tdsmaster2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TdsMaster2VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tdsmaster2gen")
	@SequenceGenerator(name = "tdsmaster2gen", sequenceName = "tdsmaster2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tdsmaster2id")
	private Long id;


	@Column(name = "fromdate")
	private LocalDate fromDate;
	@Column(name = "todate")
	private LocalDate toDate;
	@Column(name = "tcspercentage")
	private float tcsPercentage;
	@Column(name = "surpercentage")
	private float surPercentage;
	@Column(name = "edcesspercentage")
	private float edcessPercentage;
	
	
	@ManyToOne
	@JoinColumn(name = "tdsmasterid")
	@JsonBackReference
	private TdsMasterVO tdsMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
