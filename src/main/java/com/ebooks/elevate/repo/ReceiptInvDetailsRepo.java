package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ReceiptInvDetailsVO;
import com.ebooks.elevate.entity.ReceiptVO;

@Repository
public interface ReceiptInvDetailsRepo extends JpaRepository<ReceiptInvDetailsVO, Long> {

	List<ReceiptInvDetailsVO> findByReceiptVO(ReceiptVO receiptVO);

}
