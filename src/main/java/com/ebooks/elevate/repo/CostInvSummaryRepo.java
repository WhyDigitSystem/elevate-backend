package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostInvSummaryVO;
import com.ebooks.elevate.entity.CostInvoiceVO;

@Repository 
public interface CostInvSummaryRepo extends JpaRepository<CostInvSummaryVO, Long>{

	List<CostInvSummaryVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
