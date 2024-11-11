package com.ebooks.elevate.entity;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chequebook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties({"chequeBookDetailsVO"})
public class ChequeBookVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chequeboxmastergen")
	@SequenceGenerator(name = "chequeboxmastergen", sequenceName = "chequeboxmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "chequebookid")
	private Long id;
	@Column(name = "branch")
	private String branch;
	@Column(name = "chequeid")
	private String chequeId;
	@Column(name = "bank")
	private String bank;
	@Column(name = "checkprefix")
	private String checkPrefix;
	@Column(name = "checkstartno")
	private String checkStartNo;
	@Column(name = "noofchequeleaves")
	private String noOfChequeLeaves;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;

	@OneToMany(mappedBy = "chequeBookVO", cascade = CascadeType.ALL ,orphanRemoval = true)
	@JsonManagedReference
	private List<ChequeBookDetailsVO> chequeBookDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
