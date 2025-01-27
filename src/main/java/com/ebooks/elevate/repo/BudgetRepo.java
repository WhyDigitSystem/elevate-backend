package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.BudgetVO;

public interface BudgetRepo extends JpaRepository<BudgetVO, Long> {

}
