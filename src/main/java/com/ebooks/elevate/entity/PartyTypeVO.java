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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partytype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyTypeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partytypegen")
	@SequenceGenerator(name = "partytypegen", sequenceName = "partytypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partytypeid")
	private Long id;
	@Column(name = "partytype", length = 150)
	private String partyType;
	@Column(name = "partytypecode", length = 25)
	private String partyTypeCode;
	@Column(name = "lastno")
	private int lastNo = 1;
	
	@Column(name = "orgid")
	private Long orgId;
	
    @Column(name = "createdby", length = 25)
    private String createdBy;

    @Column(name = "modifyby", length = 25)
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel;

    
    
    @Column(name = "cancelremarks", length = 50)
    private String cancelRemarks;

    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
   

 
	

}
