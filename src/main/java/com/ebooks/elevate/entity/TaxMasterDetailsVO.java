package com.ebooks.elevate.entity;

import java.time.LocalDate;

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
@Table(name = "taxmasterdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxMasterDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taxmasterdetailsgen")
	@SequenceGenerator(name = "taxmasterdetailsgen", sequenceName = "taxmasterdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "taxmasterdetailsid")
	private Long id;
	@Column(name = "gst")
	private String gst;
	@Column(name = "gsttype")
	private String gstType;
	@Column(name = "percentage")
	private String percentage;
	@Column(name = "taxtype")
	private String taxType;
	@Column(name = "fromdate")
	private LocalDate fromDate;
	@Column(name = "todate")
	private String toDate;
	@Column(name = "revenueledger")
	private String revenueLedger;
	@Column(name = "costledger")
	private String costLedger;
	@Column(name = "active")
	private boolean active;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "taxmasterid")
	private TaxMasterVO taxMasterVO;
}
