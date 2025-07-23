package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.BudgetSalesPurchaseVO;

public interface BudgetSalesPurchaseRepo extends JpaRepository<BudgetSalesPurchaseVO, Long>{

	@Query(nativeQuery = true, value = "select * from budgetsalespurchase where orgid=?1 and clientcode=?2 and year=?3 and type=?4")
	List<BudgetSalesPurchaseVO> getClientBudgetSalesPurchaseDtls(Long orgId, String clientCode, String year,
			String type);

}
