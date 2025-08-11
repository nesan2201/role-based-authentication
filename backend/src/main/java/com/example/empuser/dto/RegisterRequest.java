package com.example.empuser.dto;
public class RegisterRequest
{
	private String name; 
	private String email;
	private String password;
	public String getName()
	{return name;} 
	public void setName(String n)
	{this.name=n;} 
	public String getEmail()
	{return email;} 
	public void setEmail(String e)
	{this.email=e;} 
	public String getPassword()
	{return password;} 
	public void setPassword(String p)
	{this.password=p;} }