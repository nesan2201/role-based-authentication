package com.example.empuser.mapper;
import com.example.empuser.dto.UserDTO;
import com.example.empuser.model.User;
public class UserMapper {
  public static UserDTO toDTO(User user){
    if(user==null) 
    	return null;
    UserDTO d=new UserDTO();
    d.setId(user.getId()); 
    d.setName(user.getName()); 
    d.setEmail(user.getEmail());
    d.setRole(user.getRole()==null?null:user.getRole().name());
    return d;
  }
}