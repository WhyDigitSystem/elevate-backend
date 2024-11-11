package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.GstSalesVoucherVO;

public interface GstSalesVoucherRepo extends JpaRepository<GstSalesVoucherVO, Long> {

	@Query(nativeQuery = true, value = "select * from gstsalesvoucher where orgid=?1")
	List<GstSalesVoucherVO> getAllGstSalesVoucherByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from gstsalesvoucher where gstsalesvoucherid=?1")
	List<GstSalesVoucherVO> getAllGstSalesVoucherById(Long id);

	@Query(nativeQuery = true, value = "select * from gstsalesvoucher where active=1")
	List<GstSalesVoucherVO> findGstSalesVoucherByActive();

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from gstvoucherseq;")
	String findDocId();

	@Query(nativeQuery = true, value = "CALL gstvoucher_sequence_value();")
	void nextSeq();

}
