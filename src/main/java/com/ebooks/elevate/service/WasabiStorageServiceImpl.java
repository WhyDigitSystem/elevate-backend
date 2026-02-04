package com.ebooks.elevate.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
public class WasabiStorageServiceImpl implements WasabiStorageService {
	
	private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${wasabi.bucket}")
    private String bucket;

    @Value("${wasabi.presign-expire-minutes:15}")
    private long presignMinutes;

    public WasabiStorageServiceImpl(S3Client s3Client, S3Presigner presigner) {
        this.s3Client = s3Client;
        this.presigner = presigner;
    }

    public Map<String, String> uploadFileAndGetPresignedUrl(MultipartFile file) throws IOException {
        // Build date-based key: HRMS/YYYY/MM/DD/{uuid}.{ext}
        LocalDate now = LocalDate.now();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        String filename = (ext == null || ext.isEmpty()) ? uuid : uuid + "." + ext;
        String key = String.format("HRMS/%d/%02d/%02d/%s", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), filename);

        // Upload
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Build presigned GET request
        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                .getObjectRequest(getReq)
                .signatureDuration(Duration.ofMinutes(presignMinutes))
                .build();

        String presignedUrl = presigner.presignGetObject(presignReq).url().toString();

        Map<String, String> result = new HashMap<>();
        result.put("filePath", key);      // store this in DB if needed
        result.put("url", presignedUrl);  // temporary access link
        return result;
    }

}
