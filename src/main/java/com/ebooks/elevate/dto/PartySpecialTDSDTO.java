package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartySpecialTDSDTO {
	
	private String tdsWithSec;
	private Long rateFrom;
	private Long rateTo;
	private Long tdsWithPer;
	private Long surchargePer;
	private Long edPercentage;
	private String tdsCertifiNo;

}
