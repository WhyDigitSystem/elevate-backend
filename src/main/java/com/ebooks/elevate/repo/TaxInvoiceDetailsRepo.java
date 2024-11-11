package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.TaxInvoiceDetailsVO;
import com.ebooks.elevate.entity.TaxInvoiceVO;

public interface TaxInvoiceDetailsRepo extends JpaRepository<TaxInvoiceDetailsVO,Long>{

	List<TaxInvoiceDetailsVO> findByTaxInvoiceVO(TaxInvoiceVO taxInvoiceVO);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    ledger,\r\n"
			+ "    CASE \r\n"
			+ "        WHEN SUM(fcamount) = 0 THEN SUM(lcamount) \r\n"
			+ "        ELSE SUM(fcamount) \r\n"
			+ "    END AS CrBillAmount,\r\n"
			+ "    SUM(lcamount) AS CrLcAmount\r\n"
			+ "FROM \r\n"
			+ "    taxinvoicedetails where taxinvoiceid=?1\r\n"
			+ "GROUP BY \r\n"
			+ "    ledger")
	Set<Object[]> getGSTDetails(Long id);


	
}
