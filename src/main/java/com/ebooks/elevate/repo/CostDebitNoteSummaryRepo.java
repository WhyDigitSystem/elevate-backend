package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostDebitNoteSummaryVO;
import com.ebooks.elevate.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteSummaryRepo extends JpaRepository<CostDebitNoteSummaryVO, Long>{

	List<CostDebitNoteSummaryVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
