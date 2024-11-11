package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostDebitNoteGstVO;
import com.ebooks.elevate.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteGstRepo extends JpaRepository<CostDebitNoteGstVO, Long>{

	List<CostDebitNoteGstVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
