package com.alien.bloodbrother.controllers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alien.bloodbrother.models.AuthRequest;
import com.alien.bloodbrother.models.Donar;
import com.alien.bloodbrother.models.MailRequest;
import com.alien.bloodbrother.models.OTP;
import com.alien.bloodbrother.models.UsersData;
import com.alien.bloodbrother.repository.DonarRepositry;
import com.alien.bloodbrother.repository.UserRepository;
import com.alien.bloodbrother.services.EmailService;
import com.alien.bloodbrother.util.JwtUtil;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("resource")
public class ResourceController {
	
	@Value("${spring.mail.username}")
	private String fromid;
	
	@Autowired
	MailRequest mailRequest;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserRepository repositry;
	
	@Autowired
	DonarRepositry dRepositry;
	
	@Autowired
	UsersData user;
	
	@Autowired
	Donar donar;
	
	@Autowired
	OTP otp;
	private String status=null;
	Random rand=new Random();
	Map<String ,Object> model=new HashMap<>();
	public void setMailRequest(String email,String subject)
	{
		this.mailRequest.setFromId(this.fromid);
		this.mailRequest.setToId(email);
		this.mailRequest.setSubject(subject);
	}
	@PostMapping(path ="/register",consumes = "application/json")
	public ResponseEntity<String> newRegister(@RequestBody UsersData newUser)
	{
		System.out.println(this.user);
		this.user=repositry.findByEmail(newUser.getEmail());
		if(this.user!=null)
		{
			
			return new ResponseEntity<String>("User Already exist with this Email",HttpStatus.CONFLICT);
		}
		else
		{
			this.user=newUser;
			String POT=String.valueOf(rand.nextInt(100000));
			this.otp.setOtp(POT);
			System.out.println(this.otp.getOtp());
			setMailRequest(this.user.getEmail(), "Verify One Time Password ");
			this.model.put("name",this.user.getFname()+this.user.getLname());
			this.model.put("otp",this.otp.getOtp());
			
			try
			{
				emailService.sendEmail(mailRequest, model);
				this.status="30";
				
			}
			catch (Exception e)
			{
				return new ResponseEntity<String>("Internal Server Error...Try again",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>("An One time password sent to your email",HttpStatus.OK);
		}	
	}
		
	@PostMapping(path = "/otp",consumes = "application/json")
	public ResponseEntity<String> verifyOtp(@RequestBody OTP otp)
	{
		
		if(this.otp.getOtp()!=null)
		{
			
			if(this.otp.getOtp().equals(otp.getOtp()))
			{
				
				if(this.status=="30")
				{
					
					try {
					this.user.setRole("USER");
					repositry.save(this.user);
					}
					catch (Exception e) {
						return new ResponseEntity<String>("Something went wrong with server...Try again",HttpStatus.INTERNAL_SERVER_ERROR);
					}
				
				}
				else if(this.status=="40")
				{
					try {
					dRepositry.save(this.donar);
					}
					catch (Exception e) {
						return new ResponseEntity<String>("Something went wrong with server...Try again",HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<String>("You have successfully registered",HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("Incorrect ontime password ",HttpStatus.UNAUTHORIZED);
			}
		}
		else
		{
			System.out.println("otp"+otp.getOtp());
			return new ResponseEntity<String>("Something went wrong..",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(path = "/authenticate",consumes = "application/json")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws Exception 
	{
		try 
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));    
		} 
		catch (Exception ex)
		{
			try {
				this.user=null;
				this.user=repositry.findByEmail(authRequest.getEmail());
				if(this.user==null)
					return new ResponseEntity<String>("Your are not yet registerd..Please register then try again",HttpStatus.UNAUTHORIZED);
				}
				catch (Exception e) {
					return new ResponseEntity<String>("Something went wrong...Try again",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			return new ResponseEntity<String>("Bad Credentials,Incorrect email or password",HttpStatus.UNAUTHORIZED);
		        
		    }
		    return new ResponseEntity<String>(jwtUtil.generateToken(authRequest.getEmail()),HttpStatus.OK);
	}	
	@PostMapping(path="/addDonar", consumes = "application/json")
	public ResponseEntity<String> addDonar(@RequestBody Donar donar)
	{
		this.donar=donar;
	
		String POT=String.valueOf(rand.nextInt(100000));
		this.otp.setOtp(POT);
		System.out.println("The otp"+this.otp.getOtp());
		this.model.put("name",donar.getEmail());
		this.model.put("otp",POT);
		this.setMailRequest(donar.getEmail() ,"Verify one time password");
		try
		{
			emailService.sendEmail(mailRequest, model);
			this.status="40";
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error...Try again",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("An One time password sent to your email",HttpStatus.OK);
	}
	@GetMapping(path = "/countries")
	public ResponseEntity<Set<String>> getAllcountries()
	{
		return new ResponseEntity<>(new TreeSet<>(Arrays.asList("India")),HttpStatus.OK);
	}
	@GetMapping(path = "/states")
	public ResponseEntity<Set<String>> getAllStates()
	{
		return new ResponseEntity<>(new TreeSet<>(Arrays.asList("Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal","Andaman and Nicobar Islands","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Delhi","Lakshadweep","Puducherry")),HttpStatus.OK);
	}
	
	

}
