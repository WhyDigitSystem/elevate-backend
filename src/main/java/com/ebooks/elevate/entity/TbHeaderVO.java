package com.ebooks.elevate.entity;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbheader")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TbHeaderVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tbheadergen")
	@SequenceGenerator(name = "tbheadergen", sequenceName = "tbheaderseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tbheaderid")
	private Long id;
	
	@Column(name="docid")
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name="clientcode")
	private String clientCode;
	private String month;
	@Column(name="finyear")
	private String finYear;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy;
	private boolean cancel = false;
	private boolean active;
	
	
	@OneToMany(mappedBy ="tbHeaderVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<TrailBalanceVO> trailBalanceVO;
	

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
