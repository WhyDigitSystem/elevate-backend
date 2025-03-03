package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.UserLoginClientAccessVO;
import com.ebooks.elevate.entity.UserVO;

@Repository
public interface ClientAccessRepo extends JpaRepository<UserLoginClientAccessVO, Long>{

	List<UserLoginClientAccessVO> findByUserVO(UserVO userVO);

}
