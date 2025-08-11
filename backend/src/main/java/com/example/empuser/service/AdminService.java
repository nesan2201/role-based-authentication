package com.example.empuser.service;
import com.example.empuser.dto.UserDTO;
import com.example.empuser.dto.WorkDTO;
import java.util.List;
public interface AdminService {
  List<UserDTO> getAllEmployees();
  UserDTO getEmployee(Long id);
  UserDTO createEmployee(UserDTO dto, String password);
  UserDTO updateEmployee(Long id, UserDTO dto);
  void deleteEmployee(Long id);
  WorkDTO assignWork(WorkDTO dto, Long adminId);
  List<WorkDTO> getAllWorks();
}