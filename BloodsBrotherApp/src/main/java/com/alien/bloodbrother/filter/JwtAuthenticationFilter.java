package com.alien.bloodbrother.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alien.bloodbrother.services.CustomUserDetailService;
import com.alien.bloodbrother.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	CustomUserDetailService service;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
	{
		
		String authorizationHeader=httpServletRequest.getHeader("Authorization");
		String token=null,email=null;
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
		{
			token=authorizationHeader.substring(7);
			try {
				email=jwtUtil.extractUsername(token);
			}
			catch (Exception e) {
				System.out.println("Invalid token must contain 2 periods");
			}
		}
		if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails=service.loadUserByUsername(email);
			try {
				if (jwtUtil.validateToken(token,userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
				
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			catch (Exception e) {
				System.out.println("Invalid token");
			}
		}
		filterChain.doFilter(httpServletRequest,httpServletResponse);
		
	}
}


