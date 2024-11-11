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
@Table(name = "tcsmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TcsMasterVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tcsmastergen")
	@SequenceGenerator(name = "tcsmastergen", sequenceName = "tcsmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tcsmasterid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "section")
	private  String section;
	@Column(name = "sectionname")
	private String sectionName;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiyedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;
	
	

	@JsonManagedReference
	@OneToMany(mappedBy = "tcsMasterVO", cascade = CascadeType.ALL)
	private List<TcsMaster2VO> tcsMaster2VO;
	
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
