package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartyPartnerTaggingVO;

@Repository
public interface PartyPartnerTaggingRepo extends JpaRepository<PartyPartnerTaggingVO, Long> {

	List<PartyPartnerTaggingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
