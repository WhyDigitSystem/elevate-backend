package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyChargesExemptionVO;
import com.ebooks.elevate.entity.PartyMasterVO;

@Repository
public interface PartyChargesExemptionRepo extends JpaRepository<PartyChargesExemptionVO, Long>{

	List<PartyChargesExemptionVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
