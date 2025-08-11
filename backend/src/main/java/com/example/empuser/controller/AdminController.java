package com.example.empuser.controller;

import com.example.empuser.dto.UserDTO;
import com.example.empuser.dto.WorkDTO;
import com.example.empuser.service.AdminService;
import com.example.empuser.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//@CrossOrigin(origins="http://localhost:5173/")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
  private final AdminService adminService;
  private final UserRepository userRepo;
  public AdminController(AdminService adminService, UserRepository userRepo)
  { this.adminService=adminService; 
  this.userRepo=userRepo;
  }
  @GetMapping("/users") 
  public List<UserDTO> getAllEmployees()
  {
	  return adminService.getAllEmployees(); 
	  }
  @GetMapping("/users/{id}") 
  public UserDTO getEmployee(@PathVariable Long id)
  {
	  return adminService.getEmployee(id);
	  }
  @PostMapping("/users")
  public UserDTO createEmployee(@RequestBody UserDTO dto, @RequestParam String password)
  { 
	  return adminService.createEmployee(dto, password);
	  }
  @PutMapping("/users/{id}")
  public UserDTO updateEmployee(@PathVariable Long id, @RequestBody UserDTO dto)
  { 
	  return adminService.updateEmployee(id, dto); 
	  }
  @DeleteMapping("/users/{id}") 
  public void deleteEmployee(@PathVariable Long id)
  {
	  adminService.deleteEmployee(id); 
	  }
  @PostMapping("/works")
  public WorkDTO assignWork(@RequestBody WorkDTO dto, Authentication auth){
    String email = auth.getName(); 
    Long adminId = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Admin not found")).getId();
    return adminService.assignWork(dto, adminId);
  }
  @GetMapping("/works") 
  public List<WorkDTO> getAllWorks()
  {
	  return adminService.getAllWorks();
  }
}
