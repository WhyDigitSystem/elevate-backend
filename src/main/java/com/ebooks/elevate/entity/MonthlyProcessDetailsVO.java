package com.ebooks.elevate.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="monthlyprocessdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyProcessDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monthlyprocessdetailsgen")
	@SequenceGenerator(name = "monthlyprocessdetailsgen", sequenceName = "monthlyprocessdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "monthlyprocessdetailsid")
	private Long id;
	
	@Column(name = "month", length = 30)
	private String month; 
	
	@Column(name = "year", length = 10)
	private String year;
	
	@Column(name = "clientcode", length = 150)
	private String clientCode;
	
	@Column(name = "client", length = 150)
	private String client;
	
	@Column(name = "accountcode", length = 50)
	private String elglCode;
	
	@Column(name = "accountname", length = 150)
	private String elglLedger;
	
	@Column(name = "natureofaccount", length = 50)
	private String natureOfAccount;
	
	@Column(name = "groupname", length = 150)
	private String segment;
	
	@Column(name = "clbalance", precision = 20,scale = 2)
	private BigDecimal closingBalanceCurrentMonth;
	
	@Column(name = "clbalancepremonth", precision = 20,scale = 2)
	private BigDecimal closingBalancePreviousMonth;
	
	@Column(name = "actual", precision = 20,scale = 2)
	private BigDecimal actualCurrentMonth;
	
	@Column(name = "budget", precision = 20,scale = 2)
	private BigDecimal budget;
	
	@Column(name = "pyactual", precision = 20,scale = 2)
	private BigDecimal pyActual;
	
	@Column(name = "mismatch",length = 10)
	private String mismatch;
	
	@Column(name = "approvestatus")
	private boolean approveStatus;
	
	@Column(name = "provision", precision = 20,scale = 2)
	private BigDecimal provision;
	
	@Column(name = "finalactual", precision = 20,scale = 2)
	private BigDecimal finalActual;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "monthlyprocessid")
	private MonthlyProcessVO monthlyProcessVO;

}
