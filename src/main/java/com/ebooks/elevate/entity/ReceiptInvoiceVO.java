package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "receiptinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiptinvoicegen")
	@SequenceGenerator(name = "receiptinvoicegen", sequenceName = "receiptinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "receiptinvoiceid")
	private Long id;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "invoicedt")
	private LocalDateTime invoiceDt;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "refdate")
	private LocalDateTime refDate;
	@Column(name = "masterref")
	private String masterRef;
	@Column(name = "houseref")
	private String houseRef;
	@Column(name = "curr")
	private String curr;
	@Column(name = "exrate")
	private BigDecimal exRate;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "chargableamount")
	private BigDecimal chargableAmount;
	@Column(name = "outstanding")
	private String outStanding;
	@Column(name = "settled")
	private String settled;
	@Column(name = "recexrate")
	private BigDecimal recExRate;
	@Column(name = "txnsettled")
	private BigDecimal txnSettled;
	@Column(name = "gainorloss")
	private BigDecimal gainOrLoss;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "receiptreversalid")
	ReceiptReversalVO receiptReversalVO;
}
