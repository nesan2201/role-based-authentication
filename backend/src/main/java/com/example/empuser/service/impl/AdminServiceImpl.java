package com.example.empuser.service.impl;
import com.example.empuser.service.AdminService;
import com.example.empuser.dto.UserDTO;
import com.example.empuser.dto.WorkDTO;
import com.example.empuser.mapper.UserMapper;
import com.example.empuser.mapper.WorkMapper;
import com.example.empuser.model.*;
import com.example.empuser.repository.UserRepository;
import com.example.empuser.repository.WorkRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AdminServiceImpl implements AdminService {
  private final UserRepository userRepo;
  private final WorkRepository workRepo; 
  private final PasswordEncoder encoder;
  public AdminServiceImpl(UserRepository userRepo, WorkRepository workRepo, PasswordEncoder encoder)
  { 
	  this.userRepo=userRepo; 
	  this.workRepo=workRepo;
	  this.encoder=encoder; }
  @Override public List<UserDTO> getAllEmployees()
  { return userRepo.findAll().stream().filter(u->u.getRole()==Role.ROLE_EMPLOYEE).map(UserMapper::toDTO).collect(Collectors.toList());
  }
  @Override public UserDTO getEmployee(Long id)
  { return UserMapper.toDTO(userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"))); 
  }
  @Override public UserDTO createEmployee(UserDTO dto, String password)
  { User u=new User(dto.getName(), dto.getEmail(), encoder.encode(password), Role.ROLE_EMPLOYEE); 
  userRepo.save(u);
  return UserMapper.toDTO(u);
  }
  @Override public UserDTO updateEmployee(Long id, UserDTO dto)
  { User u=userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found")); 
  u.setName(dto.getName());
  u.setEmail(dto.getEmail()); 
  userRepo.save(u); 
  return UserMapper.toDTO(u);
  }
  @Override public void deleteEmployee(Long id)
  { userRepo.deleteById(id); 
  }
  @Override public WorkDTO assignWork(WorkDTO dto, Long adminId)
  { 
	  User admin=userRepo.findById(adminId).orElseThrow(()->new RuntimeException("Admin not found"));
	  User emp=userRepo.findById(dto.getAssignedToId()).orElseThrow(()->new RuntimeException("Employee not found"));
	  Work w=new Work();
	  w.setTitle(dto.getTitle());
	  w.setDescription(dto.getDescription());
	  w.setAssignedTo(emp);
	  w.setCreatedBy(admin); 
	  w.setStatus(WorkStatus.NEW); 
	  workRepo.save(w); 
	  return WorkMapper.toDTO(w); }
  @Override public List<WorkDTO> getAllWorks()
  { return workRepo.findAll().stream().map(WorkMapper::toDTO).collect(Collectors.toList()); 
  }
}