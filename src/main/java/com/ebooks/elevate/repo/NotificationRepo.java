package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.NotificationVO;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationVO, Long>{

}
