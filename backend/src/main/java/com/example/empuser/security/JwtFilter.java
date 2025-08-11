package com.example.empuser.security;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService uds;
  public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService uds)
  { 
	  this.jwtUtil=jwtUtil;
  this.uds=uds; 
  }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    final String authHeader = req.getHeader("Authorization");
    String token=null; 
    String username=null;
    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
    { 
    	token=authHeader.substring(7);
    
    	try{ username=jwtUtil.extractUsername(token);
    	}
    	catch(Exception e){ } 
    }
    if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
    { UserDetails ud=uds.loadUserByUsername(username); 
    if(jwtUtil.validateToken(token, ud.getUsername()))
    { UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
    } }
    chain.doFilter(req, res);
  }
}