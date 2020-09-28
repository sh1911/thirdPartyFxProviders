package com.alien.bloodbrother.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.alien.bloodbrother.models.Donar;

@Service
public interface DonarRepositry extends PagingAndSortingRepository<Donar,Integer>{
	
	Donar findByEmail(String email);
	 @Query("FROM Donar d WHERE d.email LIKE %:searchText% OR d.country LIKE %:searchText% OR d.bloodgroup LIKE ':searchText' OR d.city LIKE %:searchText% OR d.state LIKE %:searchText% ORDER BY d.date ASC")
	 Page<Donar> findAllPatternDonars(Pageable pageable, @Param("searchText") String searchText);

}
