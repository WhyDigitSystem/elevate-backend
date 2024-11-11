package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.BankingWithdrawalVO;
import com.ebooks.elevate.entity.WithdrawalParticularsVO;

@Repository
public interface WithdrawalParticularsRepo extends JpaRepository<WithdrawalParticularsVO, Long>{

	List<WithdrawalParticularsVO> findByBankingWithdrawalVO(BankingWithdrawalVO bankingWithdrawalVO);

}
