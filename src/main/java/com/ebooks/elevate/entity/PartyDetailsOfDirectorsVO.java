package com.ebooks.elevate.entity;

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
@Table(name = "partydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDetailsOfDirectorsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partydetailsgen")
	@SequenceGenerator(name = "partydetailsgen", sequenceName = "partydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partydetailsid")
	private Long id;
	@Column(name = "name", length = 150)
    private String name;

    @Column(name = "designation", length = 25)
    private String designation;

    @Column(name = "phone",length = 15)
    private String phone; 

    @Column(name = "email", length = 30)
    private String email;
	
	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}