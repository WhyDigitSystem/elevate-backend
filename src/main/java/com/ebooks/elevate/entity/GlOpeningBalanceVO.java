package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "glopeningbalance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlOpeningBalanceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "glopeningbalancegen")
	@SequenceGenerator(name = "glopeningbalancegen", sequenceName = "glopeningbalanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "glopeningbalanceid")
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "docdate")
	private LocalDate docDate =LocalDate.now();;
	@Column(name = "docid",length = 50)
	private String docId;
	@Column(name="currency",length = 10)
	private String currency;
	@Column(name = "exrate",precision = 10,scale = 2)
	private BigDecimal exRate;
	@Column(name = "refno",length = 50)
	private String refNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "supprefno",length = 50)
	private String suppRefNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "supprefdate")
	private LocalDate suppRefDate;
	@Column(name = "remarks",length = 150)
	private String remarks;
	@Builder.Default
	@Column(name = "screencode",length = 5)
	private String screenCode="GOB";
	@Builder.Default
	@Column(name="screenname",length = 20)
	private String screenName="GL OPENING BALANCE";
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;
	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "ipno", length = 15)
	private String ipNo;
	@Column(name = "latitude", length = 100)
	private String latitude;
	@Column(name = "totaldebitamount",precision = 10,scale = 2)
	private BigDecimal totalDebitAmount;
	@Column(name = "totalcreditamount",precision = 10,scale = 2)
	private BigDecimal totalCreditAmount;

	@OneToMany(mappedBy = "glOpeningBalanceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ParticularsGlOpeningBalanceVO>particularsGlOpeningBalanceVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
