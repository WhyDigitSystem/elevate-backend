package com.ebooks.elevate.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ebooks.elevate.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailymonthlyexrates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMonthlyExRatesVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailymonthlyexratesgen")
	@SequenceGenerator(name = "dailymonthlyexratesgen", sequenceName = "dailymonthlyexratesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailymonthlyexratesid")
	private Long id;

	@Column(name = "date")
	private LocalDate date;
	@Column(name = "month")
	private String month;
	@Column(name = "active")
	private boolean active;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "userid")
	private String userId;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;

	@OneToMany(mappedBy = "dailyMonthlyExRatesVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<DailyMonthlyExRatesDtlVO> dailyMonthlyExRatesDtlVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
