package com.ebooks.elevate.service;

import org.springframework.stereotype.Service;

@Service
public interface AmountInWordsConverterService {

	String convert(Long amount);
	
	

}
