package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.YearDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class YearDAO
{

	String tableName = "year";
	String sqlSelectByYearId = "SELECT * FROM %s WHERE company_id=%s";

	MiniORM myORM;

	public YearDAO()
	{
		this.myORM = Container.myORMApp;
	}

	public boolean create(YearDTO vObj)
	{
		try
		{
			if (myORM.insert(vObj))
			{
				myORM.getConn().commit();
				return true;
			}
			else
			{
				myORM.getConn().rollback();
				return false;
			}
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}

	public boolean update(YearDTO vObj)
	{
		try
		{
			if (myORM.update(vObj))
			{
				myORM.getConn().commit();
				return true;
			}
			else
			{
				myORM.getConn().rollback();
				return false;
			}
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}

	public List<YearDTO> retrieveByCompanyId(long id)
	{

		String sql = String.format(sqlSelectByYearId, tableName, String.valueOf(id));
		List<YearDTO> yearDTOs = myORM.get(YearDTO.class, sql);
		return yearDTOs;
	}

	public boolean delete(YearDTO objYear)
	{
		boolean result;
		try
		{
			myORM.delete(objYear);
			result = true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}

		return result;
	}

}
