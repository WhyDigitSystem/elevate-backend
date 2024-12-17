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
@Table(name = "tbexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrailBalanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tbgen")
	@SequenceGenerator(name = "tbgen", sequenceName = "tbseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tbid")
	private Long id;

	@Column(name = "accountname")
	private String accountName;
	@Column(name = "accountcode")
	private String accountCode;
	@Column(name = "clientcode")
	private String clientCode;
	private String month;
	private BigDecimal credit;
	private BigDecimal debit;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	private boolean cancel = false;
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "tbheaderid")
	@JsonBackReference
	private TbHeaderVO tbHeaderVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
