package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}

