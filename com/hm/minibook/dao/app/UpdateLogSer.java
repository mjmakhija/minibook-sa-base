package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.UpdateLogDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class UpdateLogSer
{

	UpdateLogDAO updateLogDAO;
	MiniORM myORM;

	public UpdateLogSer()
	{
		this.myORM = Container.myORMApp;
		updateLogDAO = new UpdateLogDAO(myORM);
	}

	public UpdateLogDTO getLatestInstalledVersion()
	{
		return updateLogDAO.retrieveLatestInstalledVersion();
	}

	public boolean create(UpdateLogDTO vObj)
	{
		boolean result;

		result = updateLogDAO.create(vObj);

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

	public boolean update(UpdateLogDTO vObj)
	{
		boolean result;

		result = updateLogDAO.update(vObj);

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

	public List<UpdateLogDTO> retrieve()
	{
		return updateLogDAO.retrieve();
	}

}
