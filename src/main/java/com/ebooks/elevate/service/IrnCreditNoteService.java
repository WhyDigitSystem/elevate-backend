package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.IrnCreditDTO;
import com.ebooks.elevate.entity.IrnCreditVO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface IrnCreditNoteService {
	
//	IrnCredit
	List<IrnCreditVO> getAllIrnCreditByOrgId(Long orgId);

	Map<String, Object> updateCreateIrnCredit(@Valid IrnCreditDTO irnCreditDTO) throws ApplicationException;

	List<IrnCreditVO> getAllIrnCreditById(Long id);

	List<IrnCreditVO> getIrnCreditByActive();
	
	
	String getIrnCreditNoteDocId(Long orgId, String finYear, String branch, String branchCode);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);
}
