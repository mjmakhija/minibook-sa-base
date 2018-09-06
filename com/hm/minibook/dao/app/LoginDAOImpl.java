package com.hm.minibook.dao.app;

import com.hm.minibook.dto.app.LoginDTO;
import com.hm.miniorm.MiniORM;
import java.util.List;

public class LoginDAOImpl implements LoginDAO
{

	String tableName = "login";
	String sqlSelectByUsername = "SELECT * FROM %s WHERE username='%s'";

	MiniORM myORM;

	public LoginDAOImpl(MiniORM myORM)
	{
		this.myORM = myORM;
	}

	@Override
	public LoginDTO findByUsername(String username)
	{
		String sql = String.format(sqlSelectByUsername, tableName, username);
		List<LoginDTO> logins = myORM.get(LoginDTO.class, sql);
		return logins.size() > 0 ? logins.get(0) : null;
	}

}
