package com.example.empuser.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.empuser.model.Work;
import com.example.empuser.model.User;
import java.util.List;
public interface WorkRepository extends JpaRepository<Work, Long> 
{ List<Work> findByAssignedTo(User user); }