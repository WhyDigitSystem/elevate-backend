package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetHeadCountDTO {
	
	    private String year;
	    private Long orgId;
	    private String client;
	    private String clientCode;
	    private String department;
	    private String employmentType;
	    private String category;
	    private String month;
	    private Integer headcount;
	    private BigDecimal ctc;
	    private String createdBy;
	    private String yearType;
	    

}
