package com.example.empuser.controller;

import com.example.empuser.dto.AuthRequest;
import com.example.empuser.dto.AuthResponse;
import com.example.empuser.dto.RegisterRequest;
import com.example.empuser.service.AuthService;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin(origins="http://localhost:5173/")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  public AuthController(AuthService authService)
  {
	  this.authService=authService; 
	  }
  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest req)
  { 
	  return authService.register(req);
	  }
  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest req)
  {
	  return authService.login(req); 
	  }
}