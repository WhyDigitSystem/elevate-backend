package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ParticularsReconcileVO;
import com.ebooks.elevate.entity.ReconcileBankVO;

@Repository
public interface ParticularsReconcileRepo extends JpaRepository<ParticularsReconcileVO, Long> {

	List<ParticularsReconcileVO> findByReconcileBankVO(ReconcileBankVO reconcileBankVO);

}
