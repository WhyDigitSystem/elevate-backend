package com.ebooks.elevate.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "taxinvoicegst")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxInvoiceGstVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxinvoicegstgen")
	@SequenceGenerator(name = "taxinvoicegstgen", sequenceName = "taxinvoicegstseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "taxinvoicegstid")
	private Long id;
	@Column(name = "gstchargeacc",length = 150)
	private String gstChargeAcc;
	@Column(name = "gstsubledgercode",length = 150)
	private String gstSubledgerCode;
	@Column(name = "gstdbbillamount", precision = 10, scale = 2)
	private BigDecimal gstDbBillAmount;
	@Column(name = "gstcrbillamount", precision = 10, scale = 2)
	private BigDecimal gstCrBillAmount;
	@Column(name = "gstdblcamount", precision = 10, scale = 2)
	private BigDecimal gstDbLcAmount;
	@Column(name = "gstcrlcamount", precision = 10, scale = 2)
	private BigDecimal gstCrLcAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "taxinvoiceid")
	TaxInvoiceVO taxInvoiceVO;
	
	// Formatted getters for BigDecimal fields
    @JsonProperty("gstDbBillAmount")
    public String getFormattedGstDbBillAmount() {
        return gstDbBillAmount != null 
            ? gstDbBillAmount.setScale(2, RoundingMode.HALF_UP).toString() 
            : "0.00";
    }

    @JsonProperty("gstCrBillAmount")
    public String getFormattedGstCrBillAmount() {
        return gstCrBillAmount != null 
            ? gstCrBillAmount.setScale(2, RoundingMode.HALF_UP).toString() 
            : "0.00";
    }

    @JsonProperty("gstDbLcAmount")
    public String getFormattedGstDbLcAmount() {
        return gstDbLcAmount != null 
            ? gstDbLcAmount.setScale(2, RoundingMode.HALF_UP).toString() 
            : "0.00";
    }

    @JsonProperty("gstCrLcAmount")
    public String getFormattedGstCrLcAmount() {
        return gstCrLcAmount != null 
            ? gstCrLcAmount.setScale(2, RoundingMode.HALF_UP).toString() 
            : "0.00";
    }
	
}
