package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.AppInfoDTO;
import com.hm.miniorm.MiniORM;
import java.util.List;
import java.util.logging.Level;

class AppInfoDAO
{

	MiniORM orm;

	List<AppInfoDTO> appInfoDTOs;

	public AppInfoDAO(MiniORM myORM)
	{
		this.orm = myORM;
	}

	public boolean update(AppInfoDTO objUnit)
	{
		boolean result;

		try
		{
			orm.update(objUnit);
			result = true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}

		return result;

	}

	public List<AppInfoDTO> retrieve()
	{
		String sql = "SELECT * FROM app";
		return orm.get(AppInfoDTO.class, sql);
	}
}
