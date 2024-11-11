package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ChargerCostInvoiceVO;
import com.ebooks.elevate.entity.CostInvoiceVO;

public interface ChargerCostInvoiceRepo extends JpaRepository<ChargerCostInvoiceVO, Long> {

	List<ChargerCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
