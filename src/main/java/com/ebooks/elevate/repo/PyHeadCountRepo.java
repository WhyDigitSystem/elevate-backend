package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.PYHeadCountVO;

public interface PyHeadCountRepo extends JpaRepository<PYHeadCountVO, Long> {

	@Query(nativeQuery = true,value = "select a.* from pyheadcount a where a.orgid=?1 and a.clientcode=?2 and a.year=?3")
	List<PYHeadCountVO> getClientBudgetDls(Long org, String clientcode, String yr);

}
