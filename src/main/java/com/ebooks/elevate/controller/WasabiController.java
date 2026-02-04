package com.ebooks.elevate.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.service.WasabiStorageService;

@RestController
@RequestMapping("/api/files")
public class WasabiController {

	private final WasabiStorageService storageService;

	public WasabiController(WasabiStorageService storageService) {
        this.storageService = storageService;
    }

	 @PostMapping("/upload")
	    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
	        try {
	            Map<String, String> result = storageService.uploadFileAndGetPresignedUrl(file);
	            return ResponseEntity.ok(result);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
	        }
	    }

}
