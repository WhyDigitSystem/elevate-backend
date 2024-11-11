package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ReceiptInvoiceVO;

@Repository
public interface ReceiptInvoiceRepo extends JpaRepository<ReceiptInvoiceVO, Long> {

}
