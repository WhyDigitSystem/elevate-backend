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
@Table(name = "paymentinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentinvoicegen")
	@SequenceGenerator(name = "paymentinvoicegen", sequenceName = "paymentinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentinvoiceid")
	private Long id;
	@Column(name = "invoicenumber")
	private String invoiceNumber;
	@Column(name = "invoicedate")
	private LocalDateTime invoiceDate;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "refdate")
	private LocalDateTime refDate;
	@Column(name = "supprefno")
	private String suppRefNo;
	@Column(name = "supprefdate")
	private LocalDateTime suppRefDate;
	@Column(name = "curr")
	private String curr;
	@Column(name = "exrate")
	private BigDecimal exRate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "outstanding")
	private String outStanding;
	@Column(name = "settled")
	private String settled;
	@Column(name = "payexrate")
	private BigDecimal payExRate;
	@Column(name = "txnsettled")
	private String txnSettled;
	@Column(name = "gainorloss")
	private String gainOrLoss;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "paymentreversalid")
	PaymentReversalVO paymentReversalVO;
}
