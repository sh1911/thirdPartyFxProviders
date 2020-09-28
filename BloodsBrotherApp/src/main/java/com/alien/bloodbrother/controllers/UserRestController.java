package com.alien.bloodbrother.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alien.bloodbrother.models.Donar;
import com.alien.bloodbrother.models.UsersData;
import com.alien.bloodbrother.repository.DonarRepositry;
import com.alien.bloodbrother.repository.UserRepository;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("user")
public class UserRestController {
		
	@Autowired
	UserRepository userRepositry;
	
	@Autowired
	UsersData user;
	
	@Autowired	
	Donar donar;
	
	@Autowired
	DonarRepositry donarRepositry;
	
	/*@GetMapping("/donars")
	public ResponseEntity<Page<Donar>> getAllDonars(Pageable pageable)
	{
			return  new ResponseEntity<Page<Donar>>(donarRepositry.findAll(pageable),HttpStatus.OK);
	}*/
	
	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUserById(@PathVariable String id,@RequestBody UsersData user)
	{
		System.out.println("user has called me for update"+user);
		try
		{
			System.out.println("\n\n"+id+"is Equals"+SecurityContextHolder.getContext().getAuthentication().getName());
			if(id.equals(SecurityContextHolder.getContext().getAuthentication().getName()))
			{
				
				if(!id.equals(user.getEmail()))
				{
					if(userRepositry.findByEmail(user.getEmail())==null)
					{
						user.setId(userRepositry.findByEmail(id).getId());
						user.setRole("USER");
						userRepositry.save(user);
						return new ResponseEntity<String>("User Updated Sucessfully",HttpStatus.OK);
					}
					else
						return new ResponseEntity<String>("User Already Eixsts..Try with other Email",HttpStatus.OK);
				}
				else 
				{
					user.setId(userRepositry.findByEmail(id).getId());
					user.setRole(userRepositry.findByEmail(id).getRole());
					userRepositry.save(user);
					return new ResponseEntity<String>("User Updated Sucessfully",HttpStatus.OK);
				}
			}
			else
				return new ResponseEntity<String>("you are not authorized to update others except yours",HttpStatus.FORBIDDEN);
			
		}
		catch (Exception e) {
			return new ResponseEntity<String>("User Not Found",HttpStatus.OK);
		}
		
		
	}
	@GetMapping(path = "/search/{searchText}")
	public ResponseEntity<Page<Donar>> findAll(Pageable pageable,@PathVariable String searchText)
	{
		
		return  new ResponseEntity<Page<Donar>>(donarRepositry.findAllPatternDonars(pageable,searchText),HttpStatus.OK);
	}
	@GetMapping(path ="/donars")
	public ResponseEntity<Page<Donar>> findAll(int pageNumber,int pageSize,String sortBy,String sortDir)
	{
		return new ResponseEntity<Page<Donar>>(donarRepositry.findAll(PageRequest.of(pageNumber,pageSize,sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending())),HttpStatus.OK);
	}
	@GetMapping(path = "/me")
	public ResponseEntity<UsersData> getMe()
	{
		return new ResponseEntity<UsersData>(userRepositry.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()),HttpStatus.OK);
	}
	

}
