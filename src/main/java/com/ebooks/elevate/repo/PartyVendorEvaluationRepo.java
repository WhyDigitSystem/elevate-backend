package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartyVendorEvaluationVO;

@Repository
public interface PartyVendorEvaluationRepo extends JpaRepository<PartyVendorEvaluationVO, Long> {

	PartyVendorEvaluationVO findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
