package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.TicketVO;

@Repository
public interface TicketRepo extends JpaRepository<TicketVO, Long> {

	@Query(nativeQuery = true,value="select branch from ticket where username=?1")
	List<TicketVO> findByUserName(String userName);

	@Query(nativeQuery = true,value="select branch from ticket where orgid=?1")
	List<TicketVO> getByOrgId(Long orgId);

	

}
