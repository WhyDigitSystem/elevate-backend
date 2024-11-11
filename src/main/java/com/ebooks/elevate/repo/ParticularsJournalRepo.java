package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.GeneralJournalVO;
import com.ebooks.elevate.entity.ParticularsJournalVO;

public interface ParticularsJournalRepo extends JpaRepository<ParticularsJournalVO, Long>{

	List<ParticularsJournalVO> findByGeneralJournalVO(GeneralJournalVO generalJournalVO);


}
