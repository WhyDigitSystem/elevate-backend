package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyCurrencyMappingVO;
import com.ebooks.elevate.entity.PartyMasterVO;

@Repository
public interface PartyCurrencyMappingRepo extends JpaRepository<PartyCurrencyMappingVO, Long>{

	List<PartyCurrencyMappingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}