package com.example.empuser.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "works")
public class Work {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT") 
    private String description;
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "assigned_to_id") 
    private User assignedTo;
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "created_by_id")
    private User createdBy;
//    @ManyToOne
//    private User assigntedTo;
    @Enumerated(EnumType.STRING) 
    private WorkStatus status = WorkStatus.NEW;
    @Column(columnDefinition = "TEXT")
    private String employeeUpdate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    public Work() {}
    // getters/setters
    public Long getId(){return id;} 
    public void setId(Long id){this.id=id;}
    public String getTitle(){return title;} 
    public void setTitle(String title){this.title=title;}
    public String getDescription()
    {return description;}
    public void setDescription(String description)
    {this.description=description;}
    public User getAssignedTo()
    {return assignedTo;} 
    public void setAssignedTo(User assignedTo)
    {this.assignedTo=assignedTo;}
    public User getCreatedBy()
    {return createdBy;} 
    public void setCreatedBy(User createdBy)
    {this.createdBy=createdBy;}
    public WorkStatus getStatus()
    {return status;} 
    public void setStatus(WorkStatus status)
    {this.status=status;}
    public String getEmployeeUpdate()
    {return employeeUpdate;} 
    public void setEmployeeUpdate(String employeeUpdate)
    {this.employeeUpdate=employeeUpdate;}
    public LocalDateTime getCreatedAt()
    {return createdAt;} 
    public void setCreatedAt(LocalDateTime createdAt)
    {this.createdAt=createdAt;}
    public LocalDateTime getUpdatedAt()
    {return updatedAt;} 
    public void setUpdatedAt(LocalDateTime updatedAt)
    {this.updatedAt=updatedAt;}
}