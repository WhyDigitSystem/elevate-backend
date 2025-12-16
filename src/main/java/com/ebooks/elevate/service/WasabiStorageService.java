package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface WasabiStorageService {

	Map<String, String> uploadFileAndGetPresignedUrl(MultipartFile file) throws IOException;

}
