package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.PySalesPurchaseVO;

public interface PySalesPurchaseRepo extends JpaRepository<PySalesPurchaseVO, Long> {

	@Query(nativeQuery = true, value = "select * from pysalespurchase where orgid=?1 and clientcode=?2 and year=?3 and type=?4")
	List<PySalesPurchaseVO> getClientPySalesPurchaseDtls(Long orgId, String clientCode, String year, String type);

}
