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
@Table(name = "chequebookdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties({"chequeBookVO"})
public class ChequeBookDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chequebookdetailsgen")
	@SequenceGenerator(name = "chequebookdetailsgen", sequenceName = "chequebookdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "chequebookdetailsid")
	private Long id;
	@Column(name = "chequeno")
	private Long chequeNo;
	@Column(name = "status")
	private String status;
	@Column(name = "cancelled")
	private String cancelled;

	
	@ManyToOne
	@JoinColumn(name = "chequebookid")
	@JsonBackReference
	private ChequeBookVO chequeBookVO;

}
