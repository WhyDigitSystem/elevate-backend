package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.CostInvoiceVO;
import com.ebooks.elevate.entity.TdsCostInvoiceVO;

public interface TdsCostInvoiceRepo extends JpaRepository<TdsCostInvoiceVO,Long> {

	List<TdsCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
