package com.example.empuser.security;
import com.example.empuser.model.User;
import com.example.empuser.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepo; 
  public CustomUserDetailsService(UserRepository userRepo)
  { this.userRepo=userRepo; }
  @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  { User u=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
  return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(u.getRole().name()))); }
}