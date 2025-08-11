package com.example.empuser.service;
import com.example.empuser.dto.AuthRequest;
import com.example.empuser.dto.AuthResponse;
import com.example.empuser.dto.RegisterRequest;
public interface AuthService {
  String register(RegisterRequest req);
  AuthResponse login(AuthRequest req);
}