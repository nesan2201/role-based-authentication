package com.example.empuser.dto;
public class WorkDTO {
  private Long id; 
  private String title; 
  private String description; 
  private Long assignedToId; 
  private String assignedToName; 
  private String status; 
  private String employeeUpdate;
  public Long getId()
  {return id;} 
  public void setId(Long id)
  {this.id=id;}
  public String getTitle()
  {return title;} 
  public void setTitle(String title)
  {this.title=title;}
  public String getDescription()
  {return description;}
  public void setDescription(String description)
  {this.description=description;}
  public Long getAssignedToId()
  {return assignedToId;} 
  public void setAssignedToId(Long assignedToId)
  {this.assignedToId=assignedToId;}
  public String getAssignedToName()
  {return assignedToName;}
  public void setAssignedToName(String assignedToName)
  {this.assignedToName=assignedToName;}
  public String getStatus()
  {return status;}
  public void setStatus(String status)
  {this.status=status;}
  public String getEmployeeUpdate()
  {return employeeUpdate;} 
  public void setEmployeeUpdate(String employeeUpdate)
  {this.employeeUpdate=employeeUpdate;}
}