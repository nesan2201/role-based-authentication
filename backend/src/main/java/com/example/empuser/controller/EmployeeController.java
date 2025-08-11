package com.example.empuser.controller;

import com.example.empuser.dto.WorkDTO;
import com.example.empuser.service.EmployeeService;
import com.example.empuser.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//@CrossOrigin(origins="http://localhost:5173/")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  private final EmployeeService empService; 
  private final UserRepository userRepo;
  public EmployeeController(EmployeeService empService, UserRepository userRepo)
  {
	  this.empService=empService; 
  this.userRepo=userRepo;
  }
  @GetMapping("/works") 
  public List<WorkDTO> myWorks(Authentication auth)
  { 
	  Long userId = userRepo.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found")).getId(); 
  return empService.getMyWorks(userId); 
  }
  @PutMapping("/works/{id}/update") 
  public WorkDTO updateWork(Authentication auth, @PathVariable Long id, @RequestBody WorkDTO dto)
  {
	  Long userId = userRepo.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found")).getId();
	  return empService.updateWork(userId, id, dto); 
	  }
}