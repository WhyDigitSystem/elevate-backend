package com.ebooks.elevate.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saccode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SacCodeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "saccodegen")
	@SequenceGenerator(name = "saccodegen", sequenceName = "saccodeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "saccodeid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "serviceaccountcode",length = 50)
	private String serviceAccountCode;
	@Column(name = "sacdescription",length = 50)
	private String sacDescription;
	@Column(name = "product",length = 50)
	private String product;
	
	@Builder.Default
	@Column(name = "screencode")
	private String screenCode="HSC";
	@Builder.Default
	@Column(name="screenname")
	private String screenName="HSN SAC CODE";
	@Column(name = "createdby",length = 50)
	private String createdBy;
	@Column(name = "modifiedby",length = 50)
	private String updatedBy;
	@Column(name = "cancelremarks",length = 150)
	private String cancelRemarks;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "active")
	private boolean active;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
