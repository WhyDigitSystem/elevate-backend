package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.AccountParticularsVO;
import com.ebooks.elevate.entity.AdjustmentJournalVO;

@Repository
public interface AccountParticularsRepo extends JpaRepository<AccountParticularsVO, Long>{

	List<AccountParticularsVO> findByAdjustmentJournalVO(AdjustmentJournalVO adjustmentJournalVO);

	
}
