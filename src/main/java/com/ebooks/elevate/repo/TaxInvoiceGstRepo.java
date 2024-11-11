package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.TaxInvoiceGstVO;
import com.ebooks.elevate.entity.TaxInvoiceVO;

public interface TaxInvoiceGstRepo extends JpaRepository<TaxInvoiceGstVO, Long>{

	List<TaxInvoiceGstVO> findByTaxInvoiceVO(TaxInvoiceVO taxVO);
	

}
