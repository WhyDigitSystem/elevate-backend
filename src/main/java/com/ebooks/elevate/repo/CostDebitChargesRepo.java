package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostDebitChargesVO;
import com.ebooks.elevate.entity.CostDebitNoteVO;
@Repository
public interface CostDebitChargesRepo extends JpaRepository<CostDebitChargesVO, Long>{

	List<CostDebitChargesVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
