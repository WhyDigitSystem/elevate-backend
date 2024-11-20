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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "clientcompany")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientcompanygen")
	@SequenceGenerator(name = "clientcompanygen", sequenceName = "clientcompanyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clientcompanyid")
	private Long id;
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "companycode")
	private String companyCode;
	@Column(name = "clientname")
	private String clientName;
	@Column(name = "clientcode")
	private String clientCode;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "website")
	private String webSite;
	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
