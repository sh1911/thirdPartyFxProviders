package com.alien.bloodbrother.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("admin")
public class AdminRestController {
	
	@Autowired
	UserRepository userRepositry;
	@Autowired
	UsersData user;
	@Autowired
	Donar donar;
	@Autowired
	DonarRepositry donarRepositry;
	
	@GetMapping("/donars")
	public ResponseEntity<Page<Donar>> getAllDonars(int pageNumber,int pageSize,String sortBy,String sortDir)
	{
		
		return new ResponseEntity<Page<Donar>>(donarRepositry.findAll(PageRequest.of(pageNumber,pageSize,sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending())),HttpStatus.OK);
	}
	@GetMapping("/donars/{id}")
	public ResponseEntity<Optional<Donar>> getDonarById(@PathVariable int id)
	{
			return  new ResponseEntity<Optional<Donar>>(donarRepositry.findById(id),HttpStatus.OK);
	}
	@PutMapping("/donars/{id}")
	public ResponseEntity<String> updateDonarById(@PathVariable int id,@RequestBody Donar donar)
	{
		try
		{
		donarRepositry.save(donar);
		return new ResponseEntity<String>("Donar Updated Sucessfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping("/donars/{id}")
	public ResponseEntity<String> deleteDonarById(@PathVariable int id)
	{
		try {
		donarRepositry.deleteById(id);
		return new ResponseEntity<String>("Donar Deleted Sucessfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping("/donars/add")
	public ResponseEntity<String> createDonar(@RequestBody Donar user)
	{
		try
		{
			if(donarRepositry.findByEmail(user.getEmail())==null)
			{
				donarRepositry.save(user);
				return new ResponseEntity<String>("Donar Created Sucessfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Donar Already Eixsts..Try with other Email",HttpStatus.CONFLICT);
		
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping("/users")
	public ResponseEntity<Page<UsersData>> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir)
	{
		return new ResponseEntity<Page<UsersData>>(userRepositry.findAll(PageRequest.of(pageNumber,pageSize,sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending())),HttpStatus.OK);
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<UsersData>> getUserById(@PathVariable int id)
	{
			if(userRepositry.findById(id)!=null)
			return  new ResponseEntity<Optional<UsersData>>(userRepositry.findById(id),HttpStatus.OK);
			else
				return  new ResponseEntity<Optional<UsersData>>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/users/add")
	public ResponseEntity<String> createUser(@RequestBody UsersData user)
	{
		try
		{
			if(userRepositry.findByEmail(user.getEmail())==null)
			{
				userRepositry.save(user);
				return new ResponseEntity<String>("User Created Sucessfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("User Already Eixsted..Try with other Email",HttpStatus.CONFLICT);
		
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.BAD_REQUEST);
		}
		
	}
	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUserById(@PathVariable int id,@RequestBody UsersData user)
	{
		System.out.println("admin has called for update to id"+id);
		try
		{		
			if(userRepositry.findByEmail(user.getEmail()).getId()==id)
			{
				user.setId(id);
				userRepositry.save(user);
				return new ResponseEntity<String>("User Updated Sucessfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("User Already Eixsts..Try with other Email",HttpStatus.CONFLICT);
				
		}	
		catch (Exception e) {
			return new ResponseEntity<String>("User Not Found",HttpStatus.BAD_REQUEST);
		}
	}
			
		
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable int id)
	{
		try {
				
			if(!userRepositry.findById(id).get().getRole().equals("ADMIN"))
			{
				userRepositry.deleteById(id);
				return new ResponseEntity<String>("User Deleted Sucessfully",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("An Admin can't be Deleted!",HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>("User Not Found",HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping(path = "/donars/search/{searchText}")
	public ResponseEntity<Page<Donar>> findAllDonarsPattern(Pageable pageable,@PathVariable String searchText)
	{
	
		return  new ResponseEntity<Page<Donar>>(donarRepositry.findAllPatternDonars(pageable,searchText),HttpStatus.OK);
	}
	@GetMapping(path = "/users/search/{searchText}")
	public ResponseEntity<Page<UsersData>> findAllUsersPattern(Pageable pageable,@PathVariable String searchText)
	{
	
		return  new ResponseEntity<Page<UsersData>>(userRepositry.findAllPatternUsers(pageable,searchText),HttpStatus.OK);
	}
}
