package com.ebooks.elevate.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="monthclose")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthCloseVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthclosegen")
	@SequenceGenerator(name = "monthclosegen", sequenceName = "monthcloseseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "monthcloseid")
	private Long id;
	
	@Column(name = "orgid")
    private Long orgId;

    @Column(name = "client")
    private String client;
    
    @Column(name = "clientcode")
    private String clientCode;

    @Column(name = "finyear")
    private String finYear;
    
    @Column(name = "month")
    private String month;

    @Column(name = "monthsequnce")
    private int monthSequnce;

    @Column(name = "status")
    private String status; // OPEN / CLOSED / IN_PROGRESS

    @Column(name = "closedby")
    private String closedBy;

    @Column(name = "closeddate")
    private LocalDateTime closedDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "approvalstatus")
    private String approvalStatus; // PENDING / APPROVED / REJECTED

    @Column(name = "approvedby")
    private String approvedBy;

    @Column(name = "approveddate")
    private LocalDateTime approvedDate;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "modifiedby")
    private String modifiedBy;

    @Column(name = "modifieddate")
    private LocalDateTime modifiedDate;
    
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
