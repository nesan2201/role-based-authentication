package com.example.empuser.service;
import com.example.empuser.dto.WorkDTO;
import java.util.List;
public interface EmployeeService {
  List<WorkDTO> getMyWorks(Long userId);
  WorkDTO updateWork(Long userId, Long workId, WorkDTO dto);
}