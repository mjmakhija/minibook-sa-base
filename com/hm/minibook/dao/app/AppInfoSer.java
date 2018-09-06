package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.AppInfoDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class AppInfoSer
{

	public enum AppInfoKey
	{
		StatusId("status_id"),
		InstanceKey("instance_key"),
		ClientKey("client_key"),
		AgentKey("agent_key"),
		Name("name"),
		UpdaterVersion("updater_version"),
		UpdateStatus("update_status");

		private final String name;

		private AppInfoKey(String s)
		{
			name = s;
		}

		public boolean equalsName(String otherName)
		{
			// (otherName == null) check is not needed because name.equals(null) returns false 
			return name.equals(otherName);
		}

		public String toString()
		{
			return this.name;
		}
	}

	List<AppInfoDTO> appInfoDTOs;
	AppInfoDAO appInfoDAO;
	MiniORM myORM;

	public AppInfoSer()
	{
		this.myORM = Container.myORMApp;
		appInfoDAO = new AppInfoDAO(myORM);
		appInfoDTOs = appInfoDAO.retrieve();
	}

	public String get(AppInfoKey vAppProperty)
	{
		for (AppInfoDTO appInfoDTO : appInfoDTOs)
		{
			if (vAppProperty.toString().equals(appInfoDTO.getInfoKey()))
			{
				return String.valueOf(appInfoDTO.getInfoValue());
			}
		}
		return null;
	}

	public boolean set(AppInfoKey key, String value)
	{
		for (AppInfoDTO appInfoDTO : appInfoDTOs)
		{
			if (key.toString().equals(appInfoDTO.getInfoKey()))
			{

				appInfoDTO.setInfoValue(value);
				return update(appInfoDTO);

			}
		}

		return false;

	}

	public boolean update(AppInfoDTO appInfoDTO)
	{

		boolean result;

		result = appInfoDAO.update(appInfoDTO);

		try
		{
			if (result)
			{
				myORM.getConn().commit();
			}
			else
			{
				myORM.getConn().rollback();
			}
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}

		return result;

	}
}
