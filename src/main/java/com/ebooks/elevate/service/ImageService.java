package com.ebooks.elevate.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.entity.Image;
import com.ebooks.elevate.repo.ImageRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class ImageService {
	
	@Autowired
    private ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws IOException, java.io.IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setData(file.getBytes());
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

}