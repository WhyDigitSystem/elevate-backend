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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paymentsummary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSummaryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentsummarygen")
	@SequenceGenerator(name = "paymentsummarygen", sequenceName = "paymentsummaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentsummaryid")
	private Long id;
	@Column(name ="totaldebitamount",precision =10,scale =2)
	private float totalDebitAmount;
	@Column(name ="totalcreditamount",precision =10,scale =2)
	private float totalCreditAmount;
	
	@ManyToOne
	@JoinColumn(name ="paymentvoucherid")
	@JsonBackReference
	PaymentVoucherVO paymentVoucherVO;
	
	
}
