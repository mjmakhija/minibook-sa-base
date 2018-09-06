package com.hm.minibook.dao.app;

import com.hm.minibook.dto.app.LoginDTO;

public interface LoginDAO
{

	//public void saveUser(User user);
	public LoginDTO findByUsername(String username);
}
