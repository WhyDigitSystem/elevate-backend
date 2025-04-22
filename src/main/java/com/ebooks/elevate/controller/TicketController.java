package com.ebooks.elevate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/api/ticketcontroller")
public class TicketController {

	public static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
	
	@Autowired
	TicketService ticketService;

}
