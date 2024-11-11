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
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "accountgen")
	@SequenceGenerator(name = "accountgen", sequenceName = "accountseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "accountid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "accountgroup")
	private String accountGroup;
	@Column(name = "branchlocation")
	private String branchLocation;
	@Column(name = "accounttype")
	private String accountType;
	@Column(name = "groupname")
	private String groupName;
	@Column(name = "accountcode")
	private String accountCode;
	@Column(name = "accountname")
	private String accountName;
	@Column(name = "currency")
	private String currency;
	@Column(name = "category")
	private String category;
	@Column(name = "block")
	private boolean block;
	@Column(name = "isitcapplicable")
	private boolean isItcApplicable;
	@Column(name = "companyname")
	private String companyName;
	@Column(name = "atype")
	private String aType;
	@Column(name = "acategory")
	private String aCategory;
	@Column(name = "acurrency")
	private String aCurrency;
	@Column(name = "transid")
	private String transId;
	@Column(name = "acatcode")
	private String aCatCode;
	@Column(name = "bp")
	private String bp;
	@Column(name = "groupcode")
	private String groupCode;
	@Column(name = "gst")
	private String gst;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;

	@OneToMany(mappedBy = "accountVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Account1VO> account1VO;

	@OneToMany(mappedBy = "accountVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Account2VO> account2VO;

	@OneToMany(mappedBy = "accountVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Account3VO> account3VO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
