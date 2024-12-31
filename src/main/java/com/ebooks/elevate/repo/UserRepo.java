package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.UserVO;
@Repository
public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	@Query("select a from UserVO a where a.userName=?1")
	UserVO findByUserName(String userName);

	@Query(value = "select * from users u where u.userid=?1",nativeQuery =true)
	UserVO getUserById(Long usersId);


	UserVO findByUserNameOrEmailOrMobileNo(String userName, String userName2, String userName3);

	@Query(value = "select u from UserVO u where u.orgId =?1")
	List<UserVO> findAllByOrgId(Long orgId);

	boolean existsByUserNameOrEmailOrMobileNo(String userName, String email, String email2);

	
	@Query(nativeQuery=true,value="select a.username,b.branchcode from users a,vg_userbranch b where a.username = b.username")
	List<UserVO> getBranchCodeDetails(String userName);


//	UserVO findByUserNameAndUsersId(String userName, Long usersId);



	
	


}