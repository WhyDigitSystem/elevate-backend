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
@Table(name = "dailymonthlyexratesdtl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMonthlyExRatesDtlVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailymonthlyexratedtlsgen")
	@SequenceGenerator(name = "dailymonthlyexratesdtlgen", sequenceName = "dailymonthlyexratesdtlseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailymonthlyexratesdtlid")
	private Long id;

	@Column(name = "currency")
	private String currency;
	@Column(name = "currencydescripition")
	private String currencyDescripition;
	@Column(name = "sellingexrate",precision = 5,scale = 5)
	private float sellingExRate;
	@Column(name = "buyingexrate",precision = 5,scale = 5)
	private float buyingExrate;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "dailymonthlyexratesid")
	DailyMonthlyExRatesVO dailyMonthlyExRatesVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
