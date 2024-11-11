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
@Table(name = "tcsmaster2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TcsMaster2VO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tcsmaster2gen")
	@SequenceGenerator(name = "tcsmaster2gen", sequenceName = "tcsmaster2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tcsmaster2id")
	private Long id;
	@Column(name = "Serialno")
	private long SerialNo;
	@Column(name = "fromdate")
	private LocalDate fromDate;
	@Column(name = "todate")
	private LocalDate toDate;
	@Column(name = "tcspercentage")
	private float tcsPercentage;
	
	
	@ManyToOne
	@JoinColumn(name = "tcsmasterid")
	@JsonBackReference
	private TcsMasterVO tcsMasterVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
