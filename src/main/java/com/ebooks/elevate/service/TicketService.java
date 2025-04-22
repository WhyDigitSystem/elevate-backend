package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.TicketDTO;
import com.ebooks.elevate.entity.TicketVO;
import com.ebooks.elevate.exception.ApplicationException;

import io.jsonwebtoken.io.IOException;

@Service
public interface TicketService {

	Map<String, Object> updateTicket(@Valid TicketDTO ticketDTO) throws ApplicationException;

	TicketVO uploadTicketScreenShotInBloob(MultipartFile file, Long id) throws IOException, java.io.IOException;

	Optional<TicketVO> getTicketById(Long id);

	List<TicketVO> getTicketByUserId(Long userId);

	List<TicketVO> getTicketByOrgId(Long orgId);

}
