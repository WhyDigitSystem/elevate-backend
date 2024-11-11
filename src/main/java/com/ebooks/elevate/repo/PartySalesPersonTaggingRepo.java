package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartySalesPersonTaggingVO;

@Repository
public interface PartySalesPersonTaggingRepo extends JpaRepository<PartySalesPersonTaggingVO, Long> {

	List<PartySalesPersonTaggingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
