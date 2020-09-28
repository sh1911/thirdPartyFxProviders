package com.alien.bloodbrother.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.alien.bloodbrother.models.UsersData;

@Service
public interface UserRepository extends JpaRepository<UsersData, Integer> {

	UsersData findByEmail(String email);
	@Query("FROM UsersData u WHERE  u.email LIKE %:searchText% OR u.fname LIKE %:searchText% OR u.lname LIKE %:searchText% OR u.role LIKE %:searchText% ORDER BY u.date ASC")
	 Page<UsersData> findAllPatternUsers(Pageable pageable, @Param("searchText") String searchText);
	
}
