package com.hm.minibook.dto.app;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

@Table(name = "login")
public class LoginDTO
{

	@Column(name = "id")
	private int id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;

	public int getId()
	{
		return id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
