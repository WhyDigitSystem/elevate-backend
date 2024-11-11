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
@Table(name = "particularsdebitnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticularsDebitNoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "particularsdebitnotegen")
	@SequenceGenerator(name = "particularsdebitnotegen", sequenceName = "particularsdebitnoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "particularsdebitnoteid")
	private Long id;
	@Column(name = "tds")
	private String tds;
	@Column(name = "tdspercent")
	private String tdsPercent;
	@Column(name = "section")
	private String section;
	@Column(name = "totalTdsAmount")
	private String totalTdsAmount;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "debitnoteid")
	DebitNoteVO debitNoteVO;
}
