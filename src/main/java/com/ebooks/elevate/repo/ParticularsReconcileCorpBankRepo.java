package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ParticularsReconcileCorpBankVO;
import com.ebooks.elevate.entity.ReconcileCorpBankVO;
@Repository
public interface ParticularsReconcileCorpBankRepo extends JpaRepository<ParticularsReconcileCorpBankVO,Long>{

	List<ParticularsReconcileCorpBankVO> findByReconcileCorpBankVO(ReconcileCorpBankVO reconcileCorpBankVO);

}
