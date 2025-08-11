package com.example.empuser.service.impl;
import com.example.empuser.service.AuthService;
import com.example.empuser.dto.AuthRequest;
import com.example.empuser.dto.AuthResponse;
import com.example.empuser.dto.RegisterRequest;
import com.example.empuser.model.Role;
import com.example.empuser.model.User;
import com.example.empuser.repository.UserRepository;
import com.example.empuser.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepo; 
  private final PasswordEncoder encoder;
  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;
  public AuthServiceImpl(UserRepository userRepo, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtil jwtUtil)
  { this.userRepo=userRepo; 
  this.encoder=encoder; 
  this.authManager=authManager;
  this.jwtUtil=jwtUtil; 
  }
  @Override public String register(RegisterRequest req){
    if(userRepo.findByEmail(req.getEmail()).isPresent()) throw new RuntimeException("Email exists");
    User u=new User(req.getName(), req.getEmail(), encoder.encode(req.getPassword()), Role.ROLE_EMPLOYEE);
    userRepo.save(u);
    return "Registered";
  }
  @Override public AuthResponse login(AuthRequest req){
    authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
    User u=userRepo.findByEmail(req.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
    String token=jwtUtil.generateToken(u.getEmail(), u.getRole().name());
    return new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole().name());
  }
}