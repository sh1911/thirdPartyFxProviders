package com.alien.bloodbrother;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alien.bloodbrother.models.UsersData;
import com.alien.bloodbrother.repository.UserRepository;

@SpringBootApplication
public class BloodsBrother {

	@Autowired
	UsersData user;
	@Autowired
	UserRepository repository;

	@PostConstruct
	public void persistMe() {

			if(this.repository.findByEmail("admin@bloodsbrother.com")==null)
			{
				user.setEmail("admin@bloodsbrother.com");
				user.setFname("admin");
				user.setLname("admin");
				user.setPassword("admin");
				user.setRole("ADMIN");
				this.repository.save(user);
			}
	}
	

	public static void main(String[] args) {

		SpringApplication.run(BloodsBrother.class, args);
	}

}
