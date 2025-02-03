package com.ebooks.elevate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "serviceLeveldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceLevelDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "serviceLeveldetailsgen")
	@SequenceGenerator(name = "serviceLeveldetailsgen", sequenceName = "serviceLeveldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "serviceLeveldetailsid")
	private Long id;
	
	private String description;
	@Column(name ="elno")
	private String elNo;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="serviceLevelid")
	private ServiceLevelVO serviceLevelVO;
	
}
