package com.alien.bloodbrother.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alien.bloodbrother.models.UsersData;
import com.alien.bloodbrother.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository repositry;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	UsersData	user = repositry.findByEmail(email);
		return new CustomUserDetail(user);
	}

}
