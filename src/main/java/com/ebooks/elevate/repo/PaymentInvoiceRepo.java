package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.PaymentInvoiceVO;

public interface PaymentInvoiceRepo extends JpaRepository<PaymentInvoiceVO, Long> {

}
