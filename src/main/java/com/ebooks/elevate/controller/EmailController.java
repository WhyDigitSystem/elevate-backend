package com.ebooks.elevate.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/send")
	public String sendSimpleEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
		emailService.sendSimpleEmail(to, subject, body);
		return "✅ Simple Mail sent successfully to " + to;
	}

	/**
	 * Send HTML email
	 */
	@PostMapping("/sendHtml")
	public String sendHtmlEmail(@RequestParam String to, @RequestParam String subject) {
		// Load sample HTML content (you can use Thymeleaf or static HTML)
		String html = loadHtmlTemplate("Justin", "REQ12345");

//		emailService.sendHtmlEmail(to, subject, html);
		return "✅ HTML Mail sent successfully to " + to;
	}
	
	public String loadHtmlTemplate(String name, String requestId) {
	    try {
	        String content = Files.readString(Paths.get("src/main/resources/template/email_template.html"));
	        return content.replace("${name}", name)
	                      .replace("${requestId}", requestId);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "<p>Default email content</p>";
	    }
	}

}
