package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ReceiptOtherAccountVO;

@Repository
public interface ReceiptOtherAccountRepo extends JpaRepository<ReceiptOtherAccountVO, Long> {

}
