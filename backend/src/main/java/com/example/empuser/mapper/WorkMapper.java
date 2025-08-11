package com.example.empuser.mapper;
import com.example.empuser.dto.WorkDTO;
import com.example.empuser.model.Work;
public class WorkMapper {
  public static WorkDTO toDTO(Work w){
    if(w==null) return null;
    WorkDTO d=new WorkDTO();
    d.setId(w.getId()); 
    d.setTitle(w.getTitle()); 
    d.setDescription(w.getDescription()); 
    d.setStatus(w.getStatus()==null?null:w.getStatus().name());
    d.setEmployeeUpdate(w.getEmployeeUpdate());
    if(w.getAssignedTo()!=null)
    { d.setAssignedToId(w.getAssignedTo().getId()); 
    d.setAssignedToName(w.getAssignedTo().getName());
    }
    return d;
  }
}