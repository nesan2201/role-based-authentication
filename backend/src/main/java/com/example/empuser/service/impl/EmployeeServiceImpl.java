package com.example.empuser.service.impl;
import com.example.empuser.service.EmployeeService;
import com.example.empuser.dto.WorkDTO;
import com.example.empuser.mapper.WorkMapper;
import com.example.empuser.model.*;
import com.example.empuser.repository.UserRepository;
import com.example.empuser.repository.WorkRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final WorkRepository workRepo;
  private final UserRepository userRepo;
  public EmployeeServiceImpl(WorkRepository workRepo, UserRepository userRepo)
  { this.workRepo=workRepo;
  this.userRepo=userRepo; }
  @Override public List<WorkDTO> getMyWorks(Long userId)
  { User me=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found")); 
  return workRepo.findByAssignedTo(me).stream().map(WorkMapper::toDTO).collect(Collectors.toList()); }
  @Override public WorkDTO updateWork(Long userId, Long workId, WorkDTO dto)
  { Work w=workRepo.findById(workId).orElseThrow(()->new RuntimeException("Work not found")); 
  if(!w.getAssignedTo().getId().equals(userId)) throw new RuntimeException("Not your work");
  if(dto.getEmployeeUpdate()!=null) w.setEmployeeUpdate(dto.getEmployeeUpdate());
  if(dto.getStatus()!=null) w.setStatus(WorkStatus.valueOf(dto.getStatus()));
  Work saved=workRepo.save(w);
  return WorkMapper.toDTO(saved); }
}