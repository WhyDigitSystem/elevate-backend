package com.ebooks.elevate.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costcentertmsjobcard")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostCenterTmsJobCardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costcentertmsjobcardgen")
	@SequenceGenerator(name = "costcentertmsjobcardgen", sequenceName = "costcentertmsjobcardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "costcentertmsjobcardid")
	private Long id;
	@Column(name = "accountname",length = 50)
	private String accountName;
	@Column(name = "amount",precision = 10,scale = 2)
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "tmsjobcardid")
	@JsonBackReference
	TmsJobCardVO tmsJobCardVO;
}
