package com.ebooks.elevate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.TicketDTO;
import com.ebooks.elevate.entity.TicketVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.TicketRepo;
import com.ebooks.elevate.service.TicketService;


@Service
public class TicketServiceImpl implements TicketService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	TicketRepo ticketRepo;

	@Override
	public Map<String, Object> updateTicket(@Valid TicketDTO ticketDTO) throws ApplicationException {

		TicketVO ticketVO;

		String message = null;

		if (ObjectUtils.isEmpty(ticketDTO.getId())) {

			ticketVO = new TicketVO();

			ticketVO.setCreatedBy(ticketDTO.getCreatedBy());
			ticketVO.setUpdatedBy(ticketDTO.getCreatedBy());

			message = "Ticket Creation Successfully";
		}

		else {
			ticketVO = ticketRepo.findById(ticketDTO.getId()).orElseThrow(
					() -> new ApplicationException("Elt CompanyVO  Not Found with id: " + ticketDTO.getId()));
			ticketVO.setUpdatedBy(ticketDTO.getCreatedBy());
		}

		ticketVO = getTicketVOFroTticketDTO(ticketVO, ticketDTO);
		ticketRepo.save(ticketVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("ticketVO", ticketVO);
		return response;
	}

	private TicketVO getTicketVOFroTticketDTO(TicketVO ticketVO, @Valid TicketDTO ticketDTO) {

		ticketVO.setSubject(ticketDTO.getSubject());
		ticketVO.setDescription(ticketDTO.getDescription());
		ticketVO.setUserId(ticketDTO.getUserId());
		ticketVO.setOrgId(ticketDTO.getOrgId());
		return ticketVO;

	}

	
	@Override
	public TicketVO uploadTicketScreenShotInBloob(MultipartFile file, Long id) throws IOException {
		TicketVO ticketVO = ticketRepo.findById(id).get();
		ticketVO.setScreenShot(file.getBytes());
		return ticketRepo.save(ticketVO);
	}

	@Override
	public Optional<TicketVO> getTicketById(Long id) {
		return ticketRepo.findById(id);
	}

	@Override
	public List<TicketVO> getTicketByUserId(Long userId) {
		
		return ticketRepo.findByUserId(userId);
	}

	@Override
	public List<TicketVO> getTicketByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return ticketRepo.getByOrgId(orgId);
	}

}
