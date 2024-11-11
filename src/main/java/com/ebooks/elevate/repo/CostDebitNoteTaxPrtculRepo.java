package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostDebitNoteTaxPrtculVO;
import com.ebooks.elevate.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteTaxPrtculRepo extends JpaRepository<CostDebitNoteTaxPrtculVO, Long>{

	List<CostDebitNoteTaxPrtculVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
