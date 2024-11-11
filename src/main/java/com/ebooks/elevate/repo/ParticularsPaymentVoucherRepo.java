package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ParticularsPaymentVoucherVO;
import com.ebooks.elevate.entity.PaymentVoucherVO;

public interface ParticularsPaymentVoucherRepo extends JpaRepository<ParticularsPaymentVoucherVO, Long> {

	List<ParticularsPaymentVoucherVO> findByPaymentVoucherVO(PaymentVoucherVO paymentVoucherVO);

}
