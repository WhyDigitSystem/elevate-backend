package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.ebooks.elevate.entity.BankingDepositVO;
import com.ebooks.elevate.entity.DepositParticularsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositParticularsDTO {

	private String accountsName;
	private BigDecimal debit;
	private BigDecimal credit;
	private String narration;
}
